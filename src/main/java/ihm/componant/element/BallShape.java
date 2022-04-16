/**
 * 
 */
package ihm.componant.element;

import event.NodeEvent;
import game.element.balle.Ball;
import ihm.GameView;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

/**
 * @author Llona André--Augustine
 *
 */
public class BallShape extends Circle {

	private Ball ball;
	private BooleanProperty hasDisappeared;

	public BallShape(Ball ball) {

		this.ball = ball;
		this.setFill(ball.getColor().getColor());
		this.setCenterX(ball.getPosition().getX());
		this.setCenterY(ball.getPosition().getY());
		this.setRadius(ball.getRadius());

		this.centerXProperty().bind(ball.getPosition().getXProperty());
		this.centerYProperty().bind(ball.getPosition().getYProperty());

		this.hasDisappeared = new SimpleBooleanProperty();
		hasDisappeared.bind(ball.getHasDisappeared());
	}

	public void destroy() {

		// ball.destroy();

		this.setStroke(Color.WHITE);
		this.setStrokeWidth(2);
		this.getStrokeDashArray().addAll(5.0, 2.0);
		this.setFill(Color.TRANSPARENT);

		new Timeline(new KeyFrame(Duration.millis(300), ev -> {
			GameView.ELEMENT_EVENT.handle(new NodeEvent(true, this));
		}, new KeyValue(this.scaleXProperty(), 1.8), new KeyValue(this.scaleYProperty(), 1.8),
				new KeyValue(this.strokeProperty(), Color.TRANSPARENT), new KeyValue(this.strokeWidthProperty(), 0)))
						.play();
	}

	public void active() {
		ball.active();
	}

	public void stop() {
		ball.stop();
	}

	/**
	 * Retourne
	 * 
	 * @return the ball
	 */
	public Ball getBall() { return ball; }

	/**
	 * Retourne
	 * 
	 * @return the hasDisappeared
	 */
	public BooleanProperty getHasDisappeared() { return hasDisappeared; }
}
