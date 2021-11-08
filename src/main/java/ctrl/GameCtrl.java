package ctrl;

import java.util.ArrayList;
import java.util.List;

import app.DodgeCtrl;
import ctrl.CtrlView.ScreenName;
import factory.BallFactory;
import game.niveau.Niveau;
import game.niveau.Stage;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Node;
import javafx.scene.shape.Shape;
import node.Cuby;
import node.balle.Balle;
import node.machine.Machine;
import node.zone.Zone;
import view.GameView;
import view.MapView;

public class GameCtrl {

	private GameView gameView;

	private DodgeCtrl dodgeCtrl;

	private Niveau currentLevel;
	private Stage currentStage;

	private int indexCurrentStage;

	private BooleanProperty allMachineDestroy;

	private ArrayList<Cuby> cubys;
	private ArrayList<Machine> machines;
	private ArrayList<Zone> zones;
	private ArrayList<Balle> balles;
	
	private ArrayList<Stage> stages;
	

	public GameCtrl(GameView gameView, DodgeCtrl dodgeCtrl) {
		
		this.dodgeCtrl = dodgeCtrl;
		this.gameView = gameView;

		this.indexCurrentStage = 0;

		this.currentLevel = dodgeCtrl.getCurrentLevel();
		this.currentLevel.readLevel();
		this.stages = (ArrayList<Stage>) currentLevel.getStages(); 

		this.allMachineDestroy = new SimpleBooleanProperty(false);
	
		initListOfElement();

		this.allMachineDestroy.addListener((obs, o, n) -> {
			if (n.booleanValue()) 
				nextStage();
		});
	}

	private void initListOfElement() {

		clear();
		
		this.currentStage = stages.get(indexCurrentStage);
		this.machines.addAll(currentStage.getMachines());
		this.zones.addAll(currentStage.getZones());
		this.cubys.addAll(dodgeCtrl.getCubyPlayer());

		this.allMachineDestroy.bind(builBindingEndGame(machines, machines.size() - 1));
	}

	private void clear() {

		this.machines = new ArrayList<>();
		this.zones = new ArrayList<>();
		this.cubys = new ArrayList<>();
		this.balles = new ArrayList<>();

		this.allMachineDestroy.unbind();
	}

	public List<Node> getElement() {
		ArrayList<Node> n = new ArrayList<>();
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
				Balle b = BallFactory.get(machine.lance(), machine.getCenterX(), machine.getCenterY());
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

		if (indexCurrentStage > stages.size() - 1) {
			endLevel();
		} else {
			indexCurrentStage++;
			initListOfElement();
			gameView.clear();
		}

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

		ArrayList<Machine> engine = new ArrayList<Machine>(); 

		for (Machine m : machines) {
			if (m.getIsDestroy().get()) {
				engine.add(m); 
			}
		}
		
		for (Machine m : engine) {
			machines.remove(m);
			dodgeCtrl.getCurrentLevel().destroyMachine();
		}

	}

	public void endLevel() {

		clear();
		gameView.stopUpdate();
		
		dodgeCtrl.goToNewView(ScreenName.MAP, new MapView(dodgeCtrl));
	}

	public boolean isEndGame() {
		return (indexCurrentStage > currentLevel.getStages().size() - 1) || cubys.isEmpty();
	}
}
