package game.element;

import game.element.balle.FocusBall;
import javafx.beans.property.DoubleProperty;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/**
 * Represente la ligne de visé lorsqu'une {@link FocusBall} vise un
 * {@link Cuby}.
 * 
 * @author Llona André--Augustine
 */
public class Ligne extends Line {

	/**
	 * Constructeur de la ligne de suivi, lorsqu'une machine lance une FocusBall
	 * 
	 * @param startX Position du début de la ligne en X
	 * @param startY Position du début de la ligne en Y
	 * @param endX   Position du cuby en X
	 * @param endY   Position du cuby en Y
	 */
	public Ligne(double startX, double startY, DoubleProperty endX, DoubleProperty endY) {

		this.setStartX(startX);
		this.setStartY(startY);
		
		//Lie la fin de la ligne au coordonnée du cuby
		this.endXProperty().bind(endX);
		this.endYProperty().bind(endY);

		// Modification du style de la ligne
		this.getStrokeDashArray().addAll(50.0, 30.0);
		this.setStroke(Color.rgb(40, 45, 54)); // Couleur grise, légèrement plus claire que la couleur de fond du jeu
		this.setStrokeWidth(5);
	}
}
