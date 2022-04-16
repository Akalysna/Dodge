package game.element.balle;

import controler.DataCtrl;
import controler.DataCtrl.TypeElement;
import javafx.animation.AnimationTimer;
import util.Position;
import util.Stats;

public class GhostBall extends Ball {

	private Stats<Integer> halfLife;

	public GhostBall(Position pos, int life) {
		super(pos, TypeElement.GHOST, life);

		this.halfLife = new Stats<>(super.life.getInitial() / 2);
	}


	@Override
	protected void initBallMouvement() {
		this.mouvementTimeline = new AnimationTimer() {

			@Override
			public void handle(long now) {

				// Disparition
				if (life.getCurrent() == 0) {
					hasDisappeared.set(true);
				}

				position.setX(position.getX() + dx);
				position.setY(position.getY() + dy);


				// ---------------

				if (!position.inXInterval(0, DataCtrl.WIDTH)) {
					dx = -dx; // Direction inversé
					changeLifePoint(-1);

					if (halfLife.getCurrent() > 0) {
						// send event to ghost style
					}
				}

				if (!position.inYInterval(0, DataCtrl.HEIGHT)) {
					dy = -dy; // Direction inversé
					changeLifePoint(-1);

					if (halfLife.getCurrent() > 0) {
						// send event to ghost style
					}
				}
			}
		};

	}
}
