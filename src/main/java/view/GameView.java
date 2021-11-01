package view;

import java.util.ArrayList;

import app.DodgeCtrl;
import ctrl.GameCtrl;
import game.niveau.Niveau;
import javafx.animation.AnimationTimer;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import node.Cuby;
import node.machine.Machine;
import node.zone.Zone;

public class GameView extends AnchorPane implements Initialisable {

	private DodgeCtrl dodgeCtrl;
	private GameCtrl gameCtrl;

	private Niveau currentLevel;

	private int indexCurrentStage;

	private ArrayList<Cuby> cubys;
	private ArrayList<Machine> machines;
	private ArrayList<Zone> zones;

	private ArrayList<Node> nodes;

	public GameView(DodgeCtrl dodgeCtrl) {

		this.dodgeCtrl = dodgeCtrl;
		this.currentLevel = dodgeCtrl.getCurrentLevel();
		this.indexCurrentStage = 0;

		this.gameCtrl = new GameCtrl(this, dodgeCtrl);

		this.setBackground(new Background(new BackgroundFill(Color.rgb(27, 30, 35), null, null)));

		init();
	}

	@Override
	public void init() {

		this.getChildren().addAll(currentLevel.getStages().get(indexCurrentStage).getNodes());
		this.getChildren().addAll(dodgeCtrl.getCubyPlayer());

		update();

	}

	public void update() {
		AnimationTimer update = new AnimationTimer() {

			@Override
			public void handle(long now) {

				zoneEntered();

			}
		};
		
		update.start();

	}

	public void zoneEntered() {

		// Pour chaque zones
		for (Node n : this.getChildren()) {

			boolean intersect = false;
			
			if (n instanceof Zone) {
				Zone z = (Zone) n;

				// Si un cuby est dans la zone
				for (Cuby c : dodgeCtrl.getCubyPlayer()) {

					if (!Shape.intersect(z, c).getBoundsInLocal().isEmpty())
						intersect = true;
						
				}

				// --------------

				z.hover(intersect);
			}
		}
	}

	private void nextStage() {

		if (indexCurrentStage >= currentLevel.getStages().size()) {
			endLevel();
		} else {
			indexCurrentStage++;
		}

	}

	private void endStage() {
		// TODO Auto-generated method stub

	}

	private void endLevel() {
		// TODO Auto-generated method stub

	}

}
