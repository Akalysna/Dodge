package node.balle;

import app.DodgeCtrl;
import factory.BallFactory.TypeBalle;
import javafx.animation.AnimationTimer;

public class SimpleBall extends Balle {

	/**
	 * Constructeur de Balle
	 * 
	 * @param color   Couleur de la balle
	 * @param x       Position en x de la balle (Depuis le centre)
	 * @param y       Position en y de la balle (Depuis le centre)
	 * @param rayon   Rayon de la balle
	 * @param vitesse Vitesse de déplacement de la balle
	 */
	public SimpleBall(int x, int y, int rayon, int vitesse) {
		super(x, y, rayon, vitesse, TypeBalle.SIMPLEBALLE);
	}

	@Override
	protected void initAnimBall() {

		animBall = new AnimationTimer() {

			double dx = Math.cos(negNb(45)) * vitesse;
			double dy = Math.sin(negNb(45)) * vitesse;

			@Override
			public void handle(long arg0) {

				SimpleBall.this.setCenterX(SimpleBall.this.getCenterX() + dx);
				SimpleBall.this.setCenterY(SimpleBall.this.getCenterY() + dy);

				// ---------------

				if ((getCenterX() <= taille) || (getCenterX() >= DodgeCtrl.sceneWidth - taille)) 
					dx = -dx; // Direction inverse

				if ((getCenterY() >= DodgeCtrl.sceneHeight - taille) || (getCenterY() <= taille)) 
					dy = -dy;
			}
		};
	}

	@Override
	public void animateBall(Boolean b) {

		if (b)
			animBall.start();
		else 
			animBall.stop();
	}

}
