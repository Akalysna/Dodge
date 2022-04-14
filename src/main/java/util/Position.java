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
	
	//----------

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
	public void setPosition(double x, double y) {
		setX(x);
		setY(y);
	}
	
	public void setPosition(Position pos) {
		setX(pos.getX());
		setY(pos.getY());
	}
	
	public void addX(double x) {
		setX(getX() + x);
	}
	
	public void addY(double y) {
		setY(getY() + y);
	}

	
	// ----------------

	@Override
	public String toString() {
		return "Position (" + x + ", " + y + ")";
	}
	
	public void setX(double x) {
		this.x.set(x);
	}

	public void setY(double y) {
		this.y.set(y);
	}

	public double getX() { return x.get(); }

	public double getY() { return y.get(); }

	public DoubleProperty getXProperty() { return x; }

	public DoubleProperty getYProperty() { return y; }
}
