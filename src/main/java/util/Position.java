package util;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

/**
 * Point 2D x et y . Les coordonnées du point sont des {@link DoubleProperty}.
 * 
 * @author Llona André--Augustine
 *
 */
public class Position {

	/** Coordonné en X */
	private DoubleProperty x;

	/** Coordonné en Y */
	private DoubleProperty y;
	
	/**
	 * Contructeur de la position
	 * 
	 * @param x Coordonné en X
	 * @param y Coordonné en Y
	 */
	public Position(double x, double y) {
		this.x = new SimpleDoubleProperty(x);
		this.y = new SimpleDoubleProperty(y);
	}

	public void setX(DoubleProperty x) { this.x = x; }

	public void setY(DoubleProperty y) { this.y = y; }

	public double getX() { return x.get(); }

	public double getY() { return y.get(); }

	public DoubleProperty getXProperty() { return x; }

	public DoubleProperty getYProperty() { return y; }



}
