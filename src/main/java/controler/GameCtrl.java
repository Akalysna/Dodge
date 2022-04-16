package controler;

import java.util.ArrayList;
import java.util.List;

import event.NodeEvent;
import game.element.Cuby;
import game.element.balle.Ball;
import game.element.machine.Throwball;
import game.element.zone.Zone;
import game.niveau.Stage;
import game.niveau.World;
import ihm.GameView;
import ihm.componant.element.BallShape;
import ihm.componant.element.CubyShape;
import ihm.componant.element.ThrowballShape;
import ihm.componant.element.ZoneShape;
import javafx.scene.Node;
import javafx.scene.shape.Shape;
import util.EmptyLevelException;

/**
 * Cette classe contrôle le monde en cours
 * 
 * @author Llona André--Augustine
 *
 */
public class GameCtrl {

	/** Monde courant */
	private World world;

	/** Stage courant */
	private Stage currentStage;

	/** Index du stage courant */
	private int indexStage;

	// -----

	/** Liste des joueurs du monde */
	private ArrayList<CubyShape> cubys;

	/** Liste des machines du stage */
	private ArrayList<ThrowballShape> throwballs;

	/** Liste des zones du stage */
	private ArrayList<ZoneShape> zones;

	/** Liste des balles du stage */
	private ArrayList<BallShape> balls;

	// --------------


	/**
	 * Constructeur de GameCtrl.
	 * 
	 * @param world  Monde dans lequel le joueurs joue
	 * @param cubies Liste des joueurs dans le monde
	 */
	public GameCtrl(World world, List<Cuby> cubies) {

		this.world = world;
		this.cubys = new ArrayList<>();
		this.throwballs = new ArrayList<>();
		this.zones = new ArrayList<>();
		this.balls = new ArrayList<>();

		if (world.getNbStage() > 0) {
			indexStage = 0;
			currentStage = world.getStage(indexStage);
		}

		// Création des cubys (shape) du monde
		for (Cuby cuby : cubies) {
			cubys.add(new CubyShape(cuby));
		}

		ballEvent();
	}

	// --------------


	/**
	 * Lancement de la game
	 * 
	 * @throws EmptyLevelException Si le monde est null ou qu'il ne possède pas de
	 *                             stage
	 */
	public void start() throws EmptyLevelException {

		// Initialisation des éléments du jeu
		if (currentStage != null)
			initStageElement();

		else
			throw new EmptyLevelException("Ce monde est null ou ne comporte pas de stage");
	}


	/**
	 * Initialisation des éléments du stage et de la position des cubys
	 */
	private void initStageElement() {

		this.throwballs.clear();
		this.zones.clear();
		this.balls.clear();

		// ---Initialisation des machines et des zones---

		// Pour schaque machine du stage
		for (Throwball throwball : currentStage.getMachines()) {

			// Transformation en shape
			ThrowballShape shpThrowball = new ThrowballShape(throwball);
			shpThrowball.activeThrow(true); // Activation du lancé des balles

			throwballs.add(shpThrowball); // Ajout à la liste des machines


			// Pour chaque zone de la machine
			for (Zone zone : throwball.getZones()) {

				// Transformation en shape et ajout à la liste des zones
				zones.add(new ZoneShape(zone));
			}
		}

		// ----

		// TODO Ajouté plusieurs position pour tous les cubys dans le json

		// Met à jour la position des cubys pour le stage et du survol des zones
		for (CubyShape shpCuby : cubys) {

			shpCuby.setPosition(currentStage.getCubyPos());
			shpCuby.active(); // Active le déplacement du cuby

			shpCuby.xProperty().addListener((obj, oldV, newV) -> cubyHoveredZone());
			shpCuby.yProperty().addListener((obj, oldV, newV) -> cubyHoveredZone());
		}
	}

	// ---

	/**
	 * Évenement lié au lancer de balles par la machine
	 */
	private void ballEvent() {

		// Enregistrement de l'événement
		DataCtrl.THROW_EVENT.register(event -> {

			// Récupération de la balle lié à l'événement
			Ball ball = event.getBalle();

			if (ball != null) {

				// Transformation en shape
				BallShape ballshape = new BallShape(ball);

				// Activation, ajout et affichage
				ballshape.active();
				balls.add(ballshape);
				GameView.ELEMENT_EVENT.handle(new NodeEvent(false, ballshape));

				// -- Listener --

				// A chaque déplacement en X, vérifié qu'il n'y ai pas de colission
				// avec les cubys

				ballshape.centerXProperty().addListener((obj, o, n) -> ballCollisionCuby(ballshape));
				ballshape.centerYProperty().addListener((obj, o, n) -> ballCollisionCuby(ballshape));

				// Si la ball disparait
				ballshape.getHasDisappeared().addListener((obs, o, n) -> {

					// Détruire la balle (animation)
					ballshape.destroy();

					// Suprimé la balle
					balls.remove(ballshape);

				});
			}
		});
	}

	/**
	 * Supprime le(s) cuby(s) qui ont collisionné la balle, si collision. Les cubys
	 * sont stoppé et supprimé de l'affichage
	 * 
	 * @param ballShape Balle qui collisionne
	 */
	private void ballCollisionCuby(BallShape ballShape) {

		// Liste des cubys touché et à supprimé
		ArrayList<CubyShape> list = new ArrayList<>();

		for (CubyShape cuby : cubys) {

			// Si le cuby touche la balle
			if (!Shape.intersect(cuby, ballShape).getBoundsInLocal().isEmpty()) {

				cuby.stop();
				cubyHoveredZone();

				// Suppression du cuby à l'affichage
				GameView.ELEMENT_EVENT.handle(new NodeEvent(true, cuby));

				list.add(cuby);
			}
		}

		if (!list.isEmpty()) {
			cubys.removeAll(list);
		}

		isEndStage();

	}

	// ---

	/**
	 * Modifie l'état des zone si des cubys les survole
	 */
	private void cubyHoveredZone() {

		// Pour chaque zone
		for (ZoneShape zone : zones) {

			// Vrai si au moins un cuby est dans la zone
			boolean isIntersect = false;

			for (CubyShape cuby : this.cubys) {
				if (!Shape.intersect(zone, cuby).getBoundsInLocal().isEmpty()) {
					isIntersect = true;
				}
			}

			// S'il y a au moins 1 cuby
			if (isIntersect) {

				// S'il s'agit d'un premier cuby à survolé la zone
				if (!zone.isHovered()) {
					zone.active();
					zone.addItemInZone(+1);
				}

			} else { // Si aucun cuby sur vole la zone

				// La zone était active l'éteindre
				if (zone.isHovered()) {
					zone.stop();
					zone.addItemInZone(-1);
				}
			}
		}
	}

	// --------------

	/**
	 * Verifi s'il s'agit de la fin du stage
	 */
	private void isEndStage() {

		if (cubys.isEmpty()) {
			System.out.println("Toutes les cubys sont morts");
			endWorld();
		}

		else if (allThrowballStop()) {
			System.out.println("Toutes les machines sont eteinte");
			nextStage();
		}
	}

	/**
	 * Vérifie si toutes les machines sont désactivé
	 * 
	 * @return <code>true</code> Toutes les machines sont désactivé
	 */
	private boolean allThrowballStop() {
		// this.currentStage.getMachines()

		boolean isEndGame = true;

		for (ThrowballShape shpThrowball : throwballs) {
			isEndGame &= shpThrowball.isDestroy();
		}

		return isEndGame;
	}

	public boolean nextStage() {

		if (indexStage < world.getNbStage() - 1) {

			indexStage++;
			currentStage = world.getStage(indexStage);

			elementInGame(true);
			initStageElement();
			elementInGame(false);

			return true;
		} else {
			endWorld();
			return false;
		}
	}

	/**
	 * 
	 */
	private void elementInGame(boolean isRemove) {

		ArrayList<Node> nodes = new ArrayList<>();
		nodes.addAll(balls);
		nodes.addAll(throwballs);
		nodes.addAll(zones);

		GameView.ELEMENT_EVENT.handle(new NodeEvent(isRemove, nodes));
	}



	private void endWorld() {

		elementInGame(true);
		System.out.println("C'est fini");
	}


	// --------------------

	/**
	 * Retourne la liste des cubys du monde
	 * 
	 * @return the players
	 */
	public List<CubyShape> getCubys() { return cubys; }

	/**
	 * Retourne La liste des machines du stage
	 * 
	 * @return the throwballs
	 */
	public List<ThrowballShape> getThrowballs() { return throwballs; }

	/**
	 * Retourne la liste des zones du stage
	 * 
	 * @return the zones
	 */
	public List<ZoneShape> getZones() { return zones; }

}
