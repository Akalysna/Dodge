package ihm;

import java.util.List;

import control.view.View;
import controler.DataCtrl;
import controler.DodgeCtrl;
import controler.GameCtrl;
import event.NodeEvent;
import event.EventRegister;
import game.element.Cuby;
import game.niveau.World;
import javafx.animation.AnimationTimer;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import util.EmptyLevelException;


public class GameView extends AnchorPane implements View {

	/** Controleur du jeu. Ce charge de toutes les opérations et calcul du jeu */
	private GameCtrl gameCtrl;

	/** Boucle de mise a jour du jeu */
	private AnimationTimer update;

	private DodgeCtrl dodgeCtrl;

	public static EventRegister<NodeEvent> ELEMENT_EVENT = new EventRegister<>();


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
			
//			update = new AnimationTimer() {
//				
//				@Override
//				public void handle(long now) {
//					gameCtrl.cubyHoveredZone();
//				}
//			};
//			
//			update.start();
			
			

		} catch (EmptyLevelException e) {
			e.printStackTrace();
		}

		ELEMENT_EVENT.register(event -> {
			if (event.isRemove()) {
				this.getChildren().removeAll(event.getElement());
				System.out.println(this.getChildren());
			}
			else
				this.getChildren().addAll(event.getElement());
		});
	}

	@Override
	public void load() {
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

	/**
	 * 
	 */
	private void endGame() {

		clear();

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
