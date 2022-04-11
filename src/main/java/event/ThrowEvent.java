/**
 * 
 */
package event;

import game.element.balle.Ball;
import game.element.factory.BallFactory;
import game.element.factory.BallFactory.TypeBalle;

/**
 * @author Llona André--Augustine
 *
 */
public class ThrowEvent extends GameEvent {
	
	private Ball balle; 

	public ThrowEvent(TypeBalle type, int x, int y) {
		balle = BallFactory.get(type, x, y);
	}

	public Ball getBalle() {
		return balle;
	}

}
