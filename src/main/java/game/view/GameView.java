package game.view;

import controller.DataCtrl;
import controller.DodgeCtrl;
import controller.GameCtrl;
import javafx.animation.AnimationTimer;
import javafx.scene.Node;


public class GameView extends DodgeView {

	/** Controleur du jeu. Ce charge de toutes les opérations et calcul du jeu */
	private GameCtrl gameCtrl;

	/** Boucle de mise a jour du jeu */
	private AnimationTimer update;

	/**
	 * Constructeur permettant de crée une interface graphique du jeu
	 * 
	 * @param dodgeCtrl Controleur principal
	 */
	public GameView(DodgeCtrl dodgeCtrl) {
		super(dodgeCtrl); 
		this.gameCtrl = new GameCtrl(this, dodgeCtrl);
		this.setBackground(DataCtrl.BG_COLOR_DARK);

		initialization();
	}

	public void initialization() {

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
		this.getChildren().clear();
		this.getChildren().addAll(gameCtrl.getElement());
	}

	/** Stop la boucle du jeu */
	public void stopUpdate() {
		this.update.stop();
	}

	@Override
	public void load() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void design() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void events() {
		// TODO Auto-generated method stub
		
	}



}
