/**
 * 
 */
package ihm.componant.element;

import game.element.Cuby;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import util.Position;

/**
 * @author Llona André--Augustine
 *
 */
public class CubyShape extends Rectangle {

	private Cuby cuby;

	public CubyShape(Cuby cuby) {

		this.cuby = cuby;
		this.setFill(cuby.getColor().getColor());

		this.setWidth(cuby.getSize());
		this.setHeight(cuby.getSize());

		this.setX(100);
		this.setY(100);

		this.xProperty().bind(cuby.getPosition().getXProperty());
		this.yProperty().bind(cuby.getPosition().getYProperty());
	}

	/**
	 * Cahnge les coordonné du cuby
	 * 
	 * @param dx
	 * @param dy
	 */
	public void move(double dx, double dy) {
		cuby.move(dx, dy);
	}

	/**
	 * 
	 */
	public void setPosition(Position position) {
		cuby.getPosition().setPosition(position);
	}



}
