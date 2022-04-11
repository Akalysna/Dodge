package game.element.balle;

import controler.DataCtrl;
import controler.DataCtrl.TypeElement;
import javafx.animation.AnimationTimer;
import util.Position;

public class InvertBall extends Ball {

	public InvertBall(Position pos, int life, boolean isHorizontal) {
		super(pos, TypeElement.INVERT, life);

		dy = isHorizontal?Math.sin(0) * speed: Math.cos(0) * speed;
		dx = isHorizontal? Math.cos(0) * speed:Math.sin(0) * speed;
		
	}


	@Override
	protected void initBallMouvement() {
		this.mouvementTimeline = new AnimationTimer() {

			@Override
			public void handle(long now) {

				// Disparition
				if (life.getCurrent() == 0) {
					hasDisappeared = true;
				}

				position.setX(position.getX() + dx);
				position.setY(position.getY() + dy);


				// ---------------

				if (!position.inXInterval(0, DataCtrl.WIDTH)) {
					dx = -dx; // Direction inversé
					changeLifePoint(-1);
				}

				if (!position.inYInterval(0, DataCtrl.HEIGHT)) {
					dy = -dy; // Direction inversé
					changeLifePoint(-1);
				}
			}
		};
	}

}
