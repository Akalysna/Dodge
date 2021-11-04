package view;

import app.DodgeCtrl;
import ctrl.GameCtrl;
import javafx.animation.AnimationTimer;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;

public class GameView extends AnchorPane implements Initialisable {

	private GameCtrl gameCtrl;
	private AnimationTimer update;

	public GameView(DodgeCtrl dodgeCtrl) {

		this.gameCtrl = new GameCtrl(this, dodgeCtrl);
		this.setBackground(new Background(new BackgroundFill(Color.rgb(27, 30, 35), null, null)));

		init();
	}

	@Override
	public void init() {

		this.getChildren().addAll(gameCtrl.getElement());

		update();

	}

	public void update() {
		update = new AnimationTimer() {

			@Override
			public void handle(long now) {

				if (!gameCtrl.isEndGame()) {
					gameCtrl.updateStageStats();
					gameCtrl.zoneEntered();
					gameCtrl.balls();
					gameCtrl.cubyColision();
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
