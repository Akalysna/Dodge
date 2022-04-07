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

	public void setX(double x) {
		this.x.set(x);
	}

	public void setY(double y) {
		this.y.set(y);
	}
	
	/**
	 * 
	 */
	public boolean inXInterval(double x1, double x2) {
		return getX() >= x1 && getX() <= x2;
	}
	
	public boolean inYInterval(double y1, double y2) {
		return getY() >= y1 && getY() <= y2;
	}
	
	/**
	 * 
	 */
	public void addCoordinate(double x, double y) {
		setX(getX() + x);
		setY(getY() + y);
	}

	// ----------------

	public void setX(DoubleProperty x) { this.x = x; }

	public void setY(DoubleProperty y) { this.y = y; }

	public double getX() { return x.get(); }

	public double getY() { return y.get(); }

	public DoubleProperty getXProperty() { return x; }

	public DoubleProperty getYProperty() { return y; }

}
