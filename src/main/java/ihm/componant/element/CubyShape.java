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
	 * 
	 */
	public void setPosition(Position position) {
		cuby.getPosition().setPosition(position);
	}

	public void active() {
		cuby.active();
	}

	public void stop() {
		cuby.stop();
	}

	public boolean isMoving() {
		return cuby.isMoving();
	}

}
