package game.element.balle;

import controller.DodgeCtrl;
import game.element.factory.BallFactory.TypeBalle;
import javafx.animation.AnimationTimer;

public class InvertBall extends Balle {

	public InvertBall(double x, double y, int rayon, double vitesse, boolean isHorizontal) {
		super(x, y, rayon, vitesse, TypeBalle.INVERT);

		dy = isHorizontal?Math.sin(0) * vitesse: Math.cos(0) * vitesse;
		dx = isHorizontal? Math.cos(0) * vitesse:Math.sin(0) * vitesse;
		
//		if (isHorizontal) {
//			dy = Math.sin(0) * vitesse;
//			dx = Math.cos(0) * vitesse;
//		} else {
//			dx = Math.sin(0) * vitesse;
//			dy = Math.cos(0) * vitesse;
//		}
	}

	@Override
	protected void initAnimBall() {
		animBall = new AnimationTimer() {

			@Override
			public void handle(long arg0) {

				if (life.getCurrent() == 0) {
					destroy();
				}

				setCenterX(getCenterX() + dx);
				setCenterY(getCenterY() + dy);

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

}
