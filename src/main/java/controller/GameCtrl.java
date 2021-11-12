package controller;

import java.util.ArrayList;
import java.util.List;

import controller.ViewCtrl.ScreenName;
import game.element.Cuby;
import game.element.balle.Balle;
import game.element.factory.BallFactory;
import game.element.machine.Machine;
import game.element.zone.Zone;
import game.niveau.GestionnaireNiveau;
import game.view.GameView;
import javafx.animation.PathTransition;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Node;
import javafx.scene.shape.Shape;

public class GameCtrl {

	private GameView gameView;
	private DodgeCtrl dodgeCtrl;

	private BooleanProperty allMachineDestroy;

	private ArrayList<Cuby> cubys;
	private ArrayList<Machine> machines;
	private ArrayList<Zone> zones;
	private ArrayList<Balle> balles;
	private ArrayList<PathTransition> paths;

	private GestionnaireNiveau gestionNiveau;

	public GameCtrl(GameView gameView, DodgeCtrl dodgeCtrl) {

		this.dodgeCtrl = dodgeCtrl;
		this.gameView = gameView;

		this.gestionNiveau = dodgeCtrl.getGestionNiveau();

		this.gestionNiveau.getCurrentLevel().readLevel();
		this.gestionNiveau.setCurrentStage(0);

		this.allMachineDestroy = new SimpleBooleanProperty(false);

		initListOfElement();

		this.allMachineDestroy.addListener((obs, o, n) -> {
			if (n.booleanValue())
				nextStage();
		});
	}

	private void initListOfElement() {

		clear();

		this.machines.addAll(gestionNiveau.getCurrentStage().getMachines());
		this.zones.addAll(gestionNiveau.getCurrentStage().getZones());
		
		this.cubys.addAll(dodgeCtrl.getCubyPlayer());
		
		double x = gestionNiveau.getCurrentStage().getCubyPosition().getX();
		double y = gestionNiveau.getCurrentStage().getCubyPosition().getY();
		
		for (Cuby cuby : cubys) {
			cuby.setX(x);
			cuby.setY(y);
		}
		
		this.paths.addAll(gestionNiveau.getCurrentStage().getPathTransitions());

		this.allMachineDestroy.bind(builBindingEndGame(machines, machines.size() - 1));
	}

	private void clear() {

		if (paths != null)
			this.paths.forEach(e -> e.stop());

		this.machines = new ArrayList<>();
		this.zones = new ArrayList<>();
		this.cubys = new ArrayList<>();
		this.balles = new ArrayList<>();
		this.paths = new ArrayList<>();

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

	public void zoneEntered() {

		// Pour chaque zones
		for (Zone z : this.zones) {

			boolean intersect = false;

			// Si un cuby est dans la zone
			for (Cuby c : this.cubys) {

				if (!Shape.intersect(z, c).getBoundsInLocal().isEmpty())
					intersect = true;
			}

			// --------------

			z.hover(intersect);
		}
	}

	public void balls() {
		
		for (Machine machine : machines) {
			if (machine.isThrowBall()) {
				
				Balle b;
				
				//if(machine.getX() == 0 && machine.getY() == 0)
					b = BallFactory.get(machine.lance(), machine.getCenterX(), machine.getCenterY());
//				else 
//					b = BallFactory.get(machine.lance(), machine.getX(), machine.getY());
				b.animateBall(true);
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
			gameView.clear();
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

	public void updateStageStats() {

		ArrayList<Machine> engine = new ArrayList<>();

		for (Machine m : machines) {
			if (m.getIsDestroy().get()) {
				engine.add(m);
				
				for(PathTransition pt : paths) {
					if(pt.getNode().equals(m)) {
						pt.stop();
					}
				}
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

		DodgeCtrl.loadAndGoto(ScreenName.MAP);
	}

	public boolean isEndGame() {
		return (gestionNiveau.indexCurentStage() > gestionNiveau.nbStage() - 1) || cubys.isEmpty();
	}
}
