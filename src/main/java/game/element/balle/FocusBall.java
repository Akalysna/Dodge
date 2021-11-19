package game.element.balle;

import controller.DodgeCtrl;
import game.element.factory.BallFactory.TypeBalle;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class FocusBall extends Balle {
	
	protected FocusBall(double x, double y, int rayon, double vitesse, TypeBalle type, double xTarget, double yTarget) {
		super(x, y, rayon, vitesse, type);
		
		setDirectionTarget(xTarget, yTarget);
		
		new Timeline(new KeyFrame(Duration.seconds(3), event -> {
			animateBall(true);
		}));
	}

	@Override
	protected void initAnimBall() {
		
		animBall = new AnimationTimer() {

			@Override
			public void handle(long arg0) {
				
				if(life.getCurrent() == 0) {
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

	// Change la trajectoire de la balle
	private void setDirectionTarget(double x, double y) {

		// √((x2-x1)²+(y2-y1)²)

		double normX = x / (Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2)));
		double normY = y / (Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2)));

		this.dx = normX;
		this.dy = normY;
	}

}
