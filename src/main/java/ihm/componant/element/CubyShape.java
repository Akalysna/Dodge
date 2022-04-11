/**
 * 
 */
package ihm.componant.element;

import game.element.Cuby;
import javafx.scene.shape.Rectangle;
import util.Position;

/**
 * @author Llona André--Augustine
 *
 */
public class CubyShape extends Rectangle{
	
	private Cuby cuby; 
	
	public CubyShape(Cuby cuby) {
		
		this.cuby = cuby; 
		this.setWidth(cuby.getSize());
		this.setHeight(cuby.getSize());
		
		this.setFill(cuby.getColor().getColor());
	}
	
	/**
	 * Cahnge les coordonné du cuby
	 * @param dx
	 * @param dy
	 */
	public void move(double dx, double dy) {
		
		cuby.move(dx, dy);
		
		this.setX(cuby.getPosition().getX());
		this.setY(cuby.getPosition().getY());
	}
	
	/**
	 * 
	 */
	public void setPosition(Position position) {
		cuby.setPosition(position);
		
		this.setX(cuby.getPosition().getX());
		this.setY(cuby.getPosition().getY());
	}
	
	

}
