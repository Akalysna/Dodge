package game.element.zone;

import java.util.ArrayList;
import java.util.List;

import controler.DataCtrl.DodgeColor;
import game.element.Element;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polyline;
import javafx.util.Duration;
import util.Position;

public class Zone implements Element {

	/** Vrai si la zone est survolé */
	private boolean isHovered;

	/** Vrai si la zone est désactivé */
	private boolean isDisable;

	/** Couleur de la zone */
	private DodgeColor color;

	private List<Double> points;
	private Position position;

	// ---------------------------

	/**
	 * Constructeur de zone
	 * 
	 * @param points Liste des points pour construire la zone
	 * @param taille Taille de la zone. Chaque point est multiplié par la taille
	 * @param x      Coordonnée de la zone en X
	 * @param y      Coordonnée de la zone en Y
	 * @param color  Couleur de la zone
	 */
	public Zone(Position pos, List<Double> points, double taille, DodgeColor color) {

		this.color = color;

		this.isHovered = false;
		this.isDisable = false;

		this.points = points;
	}

	// ---------------------------

	@Override
	public void active() {
		if (!isDisable) {
			this.isHovered = true;
		}

	}

	@Override
	public void stop() {
		if (!isDisable) {
			this.isHovered = false;
		}
	}

	@Override
	public void destroy() {
		isDisable = true;
	}
	
	//-------------

	/** 
	 * Retourne
	 * @return the isHovered
	 */
	public boolean isHovered() { return isHovered; }

	/** 
	 * Retourne
	 * @return the isDisable
	 */
	public boolean isDisable() { return isDisable; }

	/** 
	 * Retourne
	 * @return the color
	 */
	public DodgeColor getColor() { return color; }

	/** 
	 * Retourne
	 * @return the points
	 */
	public List<Double> getPoints() { return points; }

	/** 
	 * Retourne
	 * @return the position
	 */
	public Position getPosition() { return position; }

	
	
	
	
	
}
