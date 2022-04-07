/**
 * 
 */
package event;

import game.element.balle.Balle;
import game.element.factory.BallFactory;
import game.element.factory.BallFactory.TypeBalle;

/**
 * @author Llona André--Augustine
 *
 */
public class ThrowEvent extends GameEvent {
	
	private Balle balle; 

	public ThrowEvent(TypeBalle type, int x, int y) {
		balle = BallFactory.get(type, x, y);
	}

	public Balle getBalle() {
		return balle;
	}

}
