package ctrl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import app.DodgeCtrl;
import game.niveau.Niveau;
import game.niveau.Stage;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Node;
import javafx.scene.shape.Shape;
import node.Cuby;
import node.machine.Machine;
import node.zone.Zone;
import view.GameView;

public class GameCtrl {

	private GameView gameView;

	private DodgeCtrl dodgeCtrl;

	private Niveau currentLevel;

	private int indexCurrentStage;

	private BooleanProperty allMachineDestroy;

	private ArrayList<Cuby> cubys;
	private ArrayList<Machine> machines;
	private ArrayList<Zone> zones;

	public GameCtrl(GameView gameView, DodgeCtrl dodgeCtrl) {

		this.dodgeCtrl = dodgeCtrl;
		this.gameView = gameView;
		this.currentLevel = dodgeCtrl.getCurrentLevel();
		
		this.indexCurrentStage = 0; 

		this.allMachineDestroy = new SimpleBooleanProperty(false);

		initListOfElement();


		this.allMachineDestroy.bind(builBindingEndGame(machines, machines.size()-1));

		this.allMachineDestroy.addListener((obs, o, n) -> {
			if (n.booleanValue()) {
				nextStage();
			}
		});
	}

	private void initListOfElement() {

		this.machines = new ArrayList<>();
		this.zones = new ArrayList<>();
		this.cubys = new ArrayList<>();

		Stage stage = currentLevel.getStages().get(indexCurrentStage);
		this.machines.addAll(stage.getMachines());
		this.zones.addAll(stage.getZones());
		this.cubys.addAll(dodgeCtrl.getCubyPlayer());
	}

	public List<Node> getElement() {
		ArrayList<Node> n = new ArrayList<>();
		n.addAll(machines);
		n.addAll(zones);
		n.addAll(cubys);
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

	private void nextStage() {

		if (indexCurrentStage >= currentLevel.getStages().size()-1) {
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
					return false;
				}
			};
		} else {

			return zbind.get(i).getIsDestroy().or(builBindingEndGame(zbind, i - 1));
		}

	}

	private void endLevel() {
		
		gameView.stopUpdate();

	}



}
