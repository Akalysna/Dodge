package game.element.balle;

import controller.DataCtrl;
import controller.DodgeCtrl;
import game.element.factory.BallFactory.TypeBalle;
import javafx.animation.AnimationTimer;
import javafx.scene.paint.Color;
import util.StatObject;

public class GhostBall extends Balle {

	private StatObject<Integer> halfLife;

	public GhostBall(double x, double y, int rayon, double vitesse) {
		super(x, y, rayon, vitesse, TypeBalle.GHOST);
		this.halfLife = new StatObject<>(life.getInitial() / 2);
	}



	@Override
	protected void initAnimBall() {

		animBall = new AnimationTimer() {

			double dx = DataCtrl.negNb(Math.cos(45)) * vitesse;
			double dy = DataCtrl.negNb(Math.sin(45)) * vitesse;

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

					if (halfLife.getCurrent() > 0)
						setGhostStyle();
					else
						setNormalStyle();
				}

				if ((getCenterY() >= DodgeCtrl.SCENE_HEIGHT - taille) || (getCenterY() <= taille)) {
					dy = -dy;
					life.setCurrent(life.getCurrent() - 1);

					if (halfLife.getCurrent() > 0)
						setGhostStyle();
					else
						setNormalStyle();
				}


			}
		};

	}

	private void setGhostStyle() {
		
		halfLife.setCurrent(halfLife.getCurrent() - 1);
		
		double purcent = life.getInitial()*2 / 100.0;
		if (color.getOpacity() - purcent < 0) {
			color = Color.color(color.getRed(), color.getGreen(), color.getBlue(), 0);
			this.setFill(color);
		}
		else {
			color = Color.color(color.getRed(), color.getGreen(), color.getBlue(), color.getOpacity() - purcent);
			this.setFill(color);
		}
	}

	private void setNormalStyle() {
		double purcent = life.getInitial()*2 / 100.0;
		if (color.getOpacity() + purcent > 1) {
			color = Color.color(color.getRed(), color.getGreen(), color.getBlue(), 1); 
			this.setFill(color);
		}
		else {
			color = Color.color(color.getRed(), color.getGreen(), color.getBlue(), color.getOpacity() + purcent); 
			this.setFill(color);
		}
	}

}
