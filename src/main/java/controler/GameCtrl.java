package controler;

import java.util.ArrayList;
import java.util.List;

import event.NodeEvent;
import game.element.Cuby;
import game.element.Element;
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

		throwballEvent();
	}

	// ------------


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

		// ----

		// Pour chaque machine du stage
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

		// TODO Ajouté plusieurs position pour tous les cubys

		// Met à jour la position des cubys pour le stage
		for (CubyShape shpCuby : cubys) {

			shpCuby.setPosition(currentStage.getCubyPos());
			shpCuby.active();

			shpCuby.xProperty().addListener((obj, oldV, newV) -> cubyHoveredZone());
			shpCuby.yProperty().addListener((obj, oldV, newV) -> cubyHoveredZone());
		}
	}

	/**
	 * Évenement lié au lancer de balles par la machine
	 */
	private void throwballEvent() {

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

	public void hoveredZone() {
		for (CubyShape cuby : cubys) {
			//cubyHoveredZone(cuby);
		}
	}

	private void cubyLeaveZone(ZoneShape zone) {

		if (zone.isHovered()) {
			zone.stop();
			zone.addItemInZone(-1);
			System.out.println(zone.getNbItemInZone());
		}

	}

	private ZoneShape zoneIntersect(Shape shape) {

		for (ZoneShape zone : zones) {

			if (!Shape.intersect(zone, shape).getBoundsInLocal().isEmpty()) { return zone; }
		}

		return null;
	}

	private void cubyEnteredZone(ZoneShape zone) {
		
		if (!zone.isHovered()) {
			zone.active();
			zone.addItemInZone(+1);
			System.out.println(zone.getNbItemInZone());
		}
	}

	private void cubyHoveredZone() {

		for (ZoneShape zone : zones) {

			boolean intersect = false;

			for (CubyShape cuby : this.cubys) {
				if (!Shape.intersect(zone, cuby).getBoundsInLocal().isEmpty()) {
					intersect = true;
				}
			}

			if (intersect) {
				cubyEnteredZone(zone);
			} else {
				cubyLeaveZone(zone);
			}
		}

//			// Si collision
//			if (!Shape.intersect(zone, cuby).getBoundsInLocal().isEmpty()) {
//
//				if (!zone.isHovered()) {
//					zone.active();
//					zone.addItemInZone(+1);
//					System.out.println(zone.getNbItemInZone());
//				}
//
//
//			} else {
//
//				if (zone.isHovered()) {
//					zone.stop();
//					zone.addItemInZone(-1);
//					System.out.println(zone.getNbItemInZone());
//				}
//			}
//		}

//		for (ZoneShape zone : zones) {
//
//			// Si le cuby entre dans la zone
//			if (!Shape.intersect(zone, cuby).getBoundsInLocal().isEmpty()) {
//
//				// Si la zone n'était pas survolé, activé la
//				if (!zone.isHovered() && cuby.isMoving())
//					zone.active();
//				else if (!cuby.isMoving())
//					zone.stop();
//			}
//
//			else {
//				if (zone.isHovered())
//					zone.stop();
//			}
//		}
	}

	/**
	 * Supprime le(s) cuby(s) qui ont colisionné la balle, si collision
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

				ZoneShape zone;

				if ((zone = zoneIntersect(cuby)) != null) {
					cubyLeaveZone(zone);
				}

				// Suppressioon du cuby à l'affichage
				GameView.ELEMENT_EVENT.handle(new NodeEvent(true, cuby));

				list.add(cuby);
			}
		}

		if (!list.isEmpty()) {
			cubys.removeAll(list);
			endWorld();
		}

	}


	public boolean nextStage() {

		if (indexStage < world.getNbStage() - 1) {
			indexStage++;
			currentStage = world.getStage(indexStage);

			initStageElement();
			return true;
		} else {
			endWorld();
			return false;
		}
	}


	private void endWorld() {

		GameView.ELEMENT_EVENT.handle(new NodeEvent(true, balls.toArray(new Node[balls.size()])));

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
