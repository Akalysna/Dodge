package view;

import app.DodgeCtrl;
import ctrl.CD;
import ctrl.GameCtrl;
import javafx.animation.AnimationTimer;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;


public class GameView extends AnchorPane implements Initialisable {

	private GameCtrl gameCtrl;
	private AnimationTimer update;

	public GameView(DodgeCtrl dodgeCtrl) {

		this.gameCtrl = new GameCtrl(this, dodgeCtrl);
		this.setBackground(CD.BG_COLOR_DARK);

		//dodgeCtrl.hideCursor(true);
		
	dodgeCtrl.hidedCursor(this);
		
		

		initNode();
	}

	@Override
	public void initNode() {

		this.getChildren().addAll(gameCtrl.getElement());
		this.setMouseTransparent(true);



		update();

	}

	public void update() {
		update = new AnimationTimer() {

			@Override
			public void handle(long now) {

				if (!gameCtrl.isEndGame()) {
					gameCtrl.zoneEntered();
					gameCtrl.balls();
					gameCtrl.cubyColision();
					gameCtrl.updateStageStats();
				} else {

					gameCtrl.endLevel();
				}

			}
		};

		update.start();

	}

	public void addNode(Node n) {
		this.getChildren().add(n);
	}

	public void removeNode(Node n) {
		this.getChildren().remove(n);
	}

	public void clear() {
		this.getChildren().clear();
		this.getChildren().addAll(gameCtrl.getElement());
	}

	public void stopUpdate() {
		this.update.stop();
	}



}
