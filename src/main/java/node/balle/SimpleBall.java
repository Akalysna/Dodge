package node.balle;

import app.DodgeCtrl;
import ctrl.CD;
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
	public SimpleBall(double x, double y, int rayon, double vitesse) {
		super(x, y, rayon, vitesse, TypeBalle.SIMPLE);
	}

	@Override
	protected void initAnimBall() {

		animBall = new AnimationTimer() {

			double dx = Math.cos(CD.negNb(45)) * vitesse;
			double dy = Math.sin(CD.negNb(45)) * vitesse;

			@Override
			public void handle(long arg0) {
				
				if(life.getCurrent() == 0) {
					destroy();
				}

				SimpleBall.this.setCenterX(SimpleBall.this.getCenterX() + dx);
				SimpleBall.this.setCenterY(SimpleBall.this.getCenterY() + dy);

				// ---------------

				if ((getCenterX() <= taille) || (getCenterX() >= DodgeCtrl.SCENE_WIDTH - taille)) {
					dx = -dx; // Direction inverse
					life.setCurrent(life.getCurrent() - 1);
				}

				if ((getCenterY() >= DodgeCtrl.SCENE_HEIGHT - taille) || (getCenterY() <= taille)) {
					dy = -dy;
					life.setCurrent(life.getCurrent() - 1);
				}
				
				
			}
		};
	}
	
	private void destroy() {
		isDestroy = true; 
		animateBall(false);
	}

	@Override
	public void animateBall(Boolean b) {

		if (b)
			animBall.start();
		else 
			animBall.stop();
	}

}
