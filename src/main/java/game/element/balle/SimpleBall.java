/**
 * 
 */
package game.element.balle;

import controller.DataCtrl;
import controller.DataCtrl.DodgeColor;
import controller.DataCtrl.DodgeShape;
import javafx.animation.AnimationTimer;
import util.Position;

/**
 * @author Llona André--Augustine
 *
 */
public class SimpleBall extends Ball {

	/**
	 * @param position
	 * @param color
	 * @param size
	 * @param shape
	 */
	protected SimpleBall(Position position, DodgeShape shape) {
		super(position, DodgeColor.WHITE, 8, DodgeShape.ROUND, 8);
	}


	@Override
	protected void ballMouvements() {
		this.mouvementTimeline = new AnimationTimer() {

			@Override
			public void handle(long now) {

				//Disparition
				if (life.getCurrent() == 0) { hasDisappeared = true; }

				position.setX(position.getX() + dx);
				position.setY(position.getY() + dy);
				

				// ---------------
				
				if(!position.inXInterval(0, DataCtrl.WIDTH)) {
					dx = -dx; // Direction inversé
					changeLifePoint(-1);
				}
				
				if(!position.inXInterval(0, DataCtrl.HEIGHT)) {
					dy = -dy; // Direction inversé
					changeLifePoint(-1);
				}
			}
		};

	}

}
