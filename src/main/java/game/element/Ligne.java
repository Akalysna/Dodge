package game.element;

import javafx.beans.property.DoubleProperty;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class Ligne extends Line {

	public Ligne(double startX, double startY,  DoubleProperty endX, DoubleProperty endY) {
		
		this.setStartX(startX);
		this.setStartY(startY);
		this.endXProperty().bind(endX);
		this.endYProperty().bind(endY);
		
		this.getStrokeDashArray().addAll(50.0, 30.0);
		this.setStroke(Color.rgb(40, 45, 54));
		this.setStrokeWidth(5);
		
	}
	
	private void destroy() {
		this.endXProperty().unbind();
		this.endYProperty().unbind();

	}
}
