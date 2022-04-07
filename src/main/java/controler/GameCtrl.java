package controler;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import app.Dodge;
import controler.DataCtrl.ScreenName;
import game.element.Cuby;
import game.element.balle.Balle;
import game.element.balle.FocusBall;
import game.element.factory.BallFactory;
import game.element.machine.Machine;
import game.element.zone.Zone;
import game.niveau.GestionnaireNiveau;
import game.view.GameView;
import javafx.animation.KeyFrame;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Node;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

public class GameCtrl {

	// TODO Penser a stop les animation de chaque élément et de la boucle du jeu

	/**
	 * Classe représentant l'interface graphique du niveau. Cette interface possède
	 * la boucle du jeu
	 */
	private GameView gameView;

	/** Controleur principal du jeu */
	private DodgeCtrl dodgeCtrl;

	/** Permet de récupérer les informations relatif au niveau */
	private GestionnaireNiveau gestionNiveau;

	/**
	 * Propriété qui lie l'état des machine. Si elles sont toutes détruites la
	 * valeur sera égale à true
	 */
	private BooleanProperty allMachineDestroy;

	private ArrayList<Cuby> cubys;
	private ArrayList<Machine> machines;
	private ArrayList<Zone> zones;
	private ArrayList<Balle> balles;
	private ArrayList<PathTransition> paths;
	private ArrayList<Line> ligne;

	private SoundCtrl home_music;

	public GameCtrl(GameView gameView, DodgeCtrl dodgeCtrl) {

		this.gameView = gameView;
		this.dodgeCtrl = dodgeCtrl;

		this.gestionNiveau = dodgeCtrl.getGestionNiveau();

		this.gestionNiveau.getCurrentLevel().readLevel();
		this.gestionNiveau.setCurrentStage(0);

		this.home_music = new SoundCtrl(gestionNiveau.getCurrentLevel().getMusicPath());
		this.home_music.play();

		this.allMachineDestroy = new SimpleBooleanProperty(false);

		init();
		initListOfElement();

		this.allMachineDestroy.addListener((obs, o, n) -> {
			if (n.booleanValue())
				nextStage();
		});


		zone();
		ball();
	}

	/**
	 * 
	 */
	private void init() {

		this.machines = new ArrayList<>();
		this.zones = new ArrayList<>();
		this.cubys = new ArrayList<>();
		this.balles = new ArrayList<>();
		this.paths = new ArrayList<>();
		this.ligne = new ArrayList<>();

	}

	private void initListOfElement() {

		clear();

		this.machines.addAll(gestionNiveau.getCurrentStage().getMachines());
		this.zones.addAll(gestionNiveau.getCurrentStage().getZones());
		this.cubys.addAll(dodgeCtrl.getPlayers(1));
		this.paths.addAll(gestionNiveau.getCurrentStage().getPathTransitions());

		double x = gestionNiveau.getCurrentStage().getCubyPosition().getX();
		double y = gestionNiveau.getCurrentStage().getCubyPosition().getY();

		for (Cuby cuby : cubys) {
			cuby.setX(x);
			cuby.setY(y);
		}

		this.allMachineDestroy.bind(builBindingEndGame(machines, machines.size() - 1));
	}

	private void clear() {

		if (paths != null)
			this.paths.forEach(e -> e.stop());

		this.machines.clear();
		this.zones.clear();
		this.cubys.clear();
		this.balles.clear();
		this.paths.clear();
		this.ligne.clear();

		this.allMachineDestroy.unbind();
	}

	public List<Node> getElement() {

		ArrayList<Node> n = new ArrayList<>();

		paths.forEach(e -> {
			n.add(e.getPath());
			e.play();
		});

		n.addAll(machines);
		n.addAll(zones);
		n.addAll(cubys);
		n.addAll(balles);

		return n;
	}

	// ----

	private void zone() {

		for (Cuby cuby : this.cubys) {

			cuby.layoutXProperty().addListener((obj, oldV, newV) -> {

				this.zones.forEach((zone) -> zone.hover(!Shape.intersect(zone, cuby).getBoundsInLocal().isEmpty()));
			});

			cuby.layoutYProperty().addListener((obj, oldV, newV) -> {
				this.zones.forEach((zone) -> zone.hover(!Shape.intersect(zone, cuby).getBoundsInLocal().isEmpty()));
			});
		}
	}

	private void ball() {

		ArrayList<Cuby> tmp = new ArrayList<>();

		for (Balle ball : this.balles) {

			ball.layoutYProperty().addListener((obj, oldV, newV) -> {
				this.cubys.forEach(cuby -> {

					if (!Shape.intersect(ball, cuby).getBoundsInLocal().isEmpty()) {
						tmp.add(cuby);
					}
				});

				this.cubys.removeAll(tmp);
			});
		}
	}

	// ----

	public void balls() {

		for (Machine machine : machines) {
			
			if (machine.isThrowBall()) {

				Balle b = BallFactory.get(machine.lance(), machine.getCenterX(), machine.getCenterY());

				if (b instanceof FocusBall) {
					drawLine(b);
				}

				balles.add(b);
				gameView.addNode(b);
			}
		}

		List<Node> tmp = new ArrayList<>();

		for (Balle b : balles) {
			if (b.isDestroy()) {
				tmp.add(b);
			}
		}

		tmp.forEach(e -> gameView.removeNode(e));
		balles.removeAll(tmp);

	}

	private void drawLine(Balle b) {

		FocusBall ball = (FocusBall) b;

		Cuby cuby = cubys.get(new Random().nextInt(cubys.size()));


		new Timeline(new KeyFrame(Duration.millis(3000), event -> {
			ball.animateBall(true);
			ball.setDirectionTarget(cuby.getX(), cuby.getY());
			if (!ligne.isEmpty())
				gameView.removeNode(this.ligne.remove(0));
		})).play();

		Line l = DataCtrl.createLine(ball.getX(), ball.getY(), cuby.xProperty(), cuby.yProperty());
		this.ligne.add(l);
		gameView.addFirstNode(l);

	}

	public void cubyColision() {

		ArrayList<Cuby> tmp = new ArrayList<>();

		for (Balle z : this.balles) {

			// Si un cuby est dans la zone
			for (Cuby c : this.cubys) {

				if (!Shape.intersect(z, c).getBoundsInLocal().isEmpty())
					tmp.add(c);
			}

			// --------------
		}

		cubys.removeAll(tmp);
		tmp.forEach(e -> gameView.removeNode(e));
	}

	private void nextStage() {

		if (gestionNiveau.nextStage()) {
			initListOfElement();
			gameView.loadLevel();
		} else
			endLevel();
	}

	private BooleanBinding builBindingEndGame(ArrayList<Machine> zbind, int i) {

		if (i < 0) {
			return new BooleanBinding() {

				@Override
				protected boolean computeValue() {
					return true;
				}
			};
		} else {

			return zbind.get(i).getIsDestroy().and(builBindingEndGame(zbind, i - 1));
		}

	}

	public void stopPathMove() {

		for (Machine m : machines) {
			if (m.getIsDestroy().get()) {
				for (PathTransition pt : paths) {
					if (pt.getNode().equals(m)) {
						pt.stop();
					}
				}
			}
		}

		for (Zone z : zones) {
			if (z.getIsDisable().get()) {
				for (PathTransition pt : paths) {
					if (pt.getNode().equals(z)) {
						pt.stop();
					}
				}
			}
		}

	}

	public void updateStageStats() {

		ArrayList<Machine> engine = new ArrayList<>();

		for (Machine m : machines) {
			if (m.getIsDestroy().get()) {
				engine.add(m);
			}
		}

		for (Machine m : engine) {
			machines.remove(m);
			gestionNiveau.getCurrentLevel().destroyMachine();
		}

	}

	public void endLevel() {

		clear();
		gameView.stopUpdate();

		Dodge.goTo(ScreenName.MAP);
	}

	public boolean isEndGame() {
		return (gestionNiveau.indexCurentStage() > gestionNiveau.nbStage() - 1) || cubys.isEmpty();
	}
}
