package ihm;

import java.util.List;

import control.view.View;
import controler.DataCtrl;
import controler.DodgeCtrl;
import controler.GameCtrl;
import game.element.Cuby;
import game.niveau.World;
import javafx.animation.AnimationTimer;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import util.EmptyLevelException;


public class GameView extends AnchorPane implements View {

	/** Controleur du jeu. Ce charge de toutes les opérations et calcul du jeu */
	private GameCtrl gameCtrl;

	/** Boucle de mise a jour du jeu */
	private AnimationTimer update;

	private Rectangle fadeRect;
	private DodgeCtrl dodgeCtrl;


	/**
	 * Constructeur permettant de crée une interface graphique du jeu
	 * 
	 * @param dodgeCtrl Controleur principal
	 */
	public GameView(DodgeCtrl dodgeCtrl) {

		this.setBackground(DataCtrl.BG_COLOR_DARK);
		this.dodgeCtrl = dodgeCtrl;


		// initialization();
	}

	public void initWorld(World world, List<Cuby> cubies) {

		gameCtrl = new GameCtrl(world, cubies);

		try {

			gameCtrl.start();
			this.getChildren().addAll(gameCtrl.getThrowballs());
			this.getChildren().addAll(gameCtrl.getZones());
			this.getChildren().addAll(gameCtrl.getCubys());

		} catch (EmptyLevelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void load() {
		// TODO Auto-generated method stub

	}



//	public void initialization() {
//
//		this.getChildren().addAll(gameCtrl.getElement());
//		this.setMouseTransparent(true);
//
//		update();
//	}
//
//	public void update() {
//		update = new AnimationTimer() {
//
//			@Override
//			public void handle(long now) {
//
//				if (!gameCtrl.isEndGame()) {
//					// gameCtrl.zoneEntered();
//					gameCtrl.balls();
//					// gameCtrl.cubyColision();
//					gameCtrl.stopPathMove();
//					gameCtrl.updateStageStats();
//				} else {
//
//					gameCtrl.endLevel();
//				}
//
//			}
//		};
//
//		update.start();
//	}


	private void clear() {
		this.getChildren().clear();
	}


//	// Manipulation des Nodes dans la scene
//
//	public void addNode(Node n) {
//		this.getChildren().add(n);
//	}
//
//	public void addFirstNode(Node n) {
//		this.getChildren().add(0, n);
//	}
//
//	public void removeNode(Node n) {
//		this.getChildren().remove(n);
//	}
//
//
//	/**
//	 * Supprime tous les éléments de l'écran et ajoute les éléments du nouveau
//	 * niveau
//	 */
//	public void loadLevel() {
//
//		stopUpdate();
//		this.getChildren().clear();
//
//		fadeRect = new Rectangle();
//		fadeRect.setFill(Color.rgb(27, 30, 35, 0));
//		fadeRect.setHeight(Dodge.SCENE_HEIGHT);
//		fadeRect.setWidth(Dodge.SCENE_WIDTH);
//
//		this.getChildren().add(fadeRect);
//
//		FillTransition fill = new FillTransition(Duration.millis(900), fadeRect, Color.rgb(27, 30, 35, 0),
//				Color.rgb(27, 30, 35, 1));
//		fill.play();
//
//		fill.setOnFinished(event -> {
//			//this.getChildren().addAll(gameCtrl.getElement());
//			update.start();
//
//		});
//	}


	/** Stop la boucle du jeu */
	public void stopUpdate() {
		this.update.stop();
	}


}
