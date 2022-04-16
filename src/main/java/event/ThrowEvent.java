/**
 * 
 */
package event;

import controler.DataCtrl.TypeElement;
import game.element.balle.Ball;
import game.element.factory.BallFactory;
import game.element.factory.BallFactory.TypeBalle;
import util.Position;

/**
 * @author Llona André--Augustine
 *
 */
public class ThrowEvent extends GameEvent {

	private Ball balle;
	private boolean isDestroy;

	public ThrowEvent(boolean isDestroy) {
		this.isDestroy = isDestroy;
		balle = null;
	}

	public ThrowEvent(TypeElement type,  double x, double y) {
		this.isDestroy = false;
		balle = BallFactory.get(type, new Position(x, y));
	}

	public Ball getBalle() { return balle; }

	public boolean isDestroy() { return isDestroy; }

}
