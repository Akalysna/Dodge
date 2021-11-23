package game.element;

import javafx.beans.property.DoubleProperty;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class Ligne extends Line {

	/**
	 * Constructeur de la ligne de suivi, lorsqu'une machine lance une FocusBall
	 * 
	 * @param startX Position du début de la ligne en X
	 * @param startY Position du début de la ligne en Y
	 * @param endX   Position de la fin de la ligne en X
	 * @param endY   Position de la fin de la ligne en X
	 */
	public Ligne(double startX, double startY, DoubleProperty endX, DoubleProperty endY) {

		this.setStartX(startX);
		this.setStartY(startY);
		this.endXProperty().bind(endX);
		this.endYProperty().bind(endY);

		this.getStrokeDashArray().addAll(50.0, 30.0);
		this.setStroke(Color.rgb(40, 45, 54)); //Couleur gris 
		this.setStrokeWidth(5);

	}
}
