package game.view;

import app.Dodge;
import control.view.View;
import controler.DataCtrl;
import controler.DodgeCtrl;
import controler.GameCtrl;
import javafx.animation.AnimationTimer;
import javafx.animation.FillTransition;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;


public class GameView extends AnchorPane implements View {

	/** Controleur du jeu. Ce charge de toutes les opérations et calcul du jeu */
	private GameCtrl gameCtrl;

	/** Boucle de mise a jour du jeu */
	private AnimationTimer update;

	private Rectangle fadeRect;

	/**
	 * Constructeur permettant de crée une interface graphique du jeu
	 * 
	 * @param dodgeCtrl Controleur principal
	 */
	public GameView(DodgeCtrl dodgeCtrl) {

		this.gameCtrl = new GameCtrl(this, dodgeCtrl);

		initialization();
	}

	public void initialization() {

		this.setBackground(DataCtrl.BG_COLOR_DARK);
		this.getChildren().addAll(gameCtrl.getElement());
		this.setMouseTransparent(true);

		update();
	}

	public void update() {
		update = new AnimationTimer() {

			@Override
			public void handle(long now) {

				if (!gameCtrl.isEndGame()) {
					//gameCtrl.zoneEntered();
					gameCtrl.balls();
					//gameCtrl.cubyColision();
					gameCtrl.stopPathMove();
					gameCtrl.updateStageStats();
				} else {

					gameCtrl.endLevel();
				}

			}
		};

		update.start();
	}


	// Manipulation des Nodes dans la scene

	public void addNode(Node n) {
		this.getChildren().add(n);
	}

	public void addFirstNode(Node n) {
		this.getChildren().add(0, n);
	}

	public void removeNode(Node n) {
		this.getChildren().remove(n);
	}


	/**
	 * Supprime tous les éléments de l'écran et ajoute les éléments du nouveau
	 * niveau
	 */
	public void loadLevel() {

		stopUpdate();
		this.getChildren().clear();

		fadeRect = new Rectangle();
		fadeRect.setFill(Color.rgb(27, 30, 35, 0));
		fadeRect.setHeight(Dodge.SCENE_HEIGHT);
		fadeRect.setWidth(Dodge.SCENE_WIDTH);

		this.getChildren().add(fadeRect);

		FillTransition fill = new FillTransition(Duration.millis(900), fadeRect, Color.rgb(27, 30, 35, 0),
				Color.rgb(27, 30, 35, 1));
		fill.play();

		fill.setOnFinished(event -> {
			this.getChildren().addAll(gameCtrl.getElement());
			update.start();

		});
	}


	/** Stop la boucle du jeu */
	public void stopUpdate() {
		this.update.stop();
	}

	@Override
	public void load() {
		// TODO Auto-generated method stub
		
	}
}
