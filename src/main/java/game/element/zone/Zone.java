package game.element.zone;

import java.util.List;

import controler.DataCtrl.DodgeColor;
import game.element.Element;
import game.element.machine.Throwball;
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

	private double size;
	private int nbItemInZone;

	private Throwball machine;

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
	public Zone(Position pos, List<Double> points, double taille, DodgeColor color, Throwball machine) {

		this.color = color;

		this.isHovered = false;
		this.isDisable = false;

		this.points = points;
		this.machine = machine;
		this.position = pos;

		this.size = taille;
		this.nbItemInZone = 0; 
	}

	public Zone(Position pos, List<Double> points, double taille, DodgeColor color) {
		this(pos, points, taille, color, null);
	}


	/**
	 * @param machine the machine to set
	 */
	public void setMachine(Throwball machine) { this.machine = machine; }

	// ---------------------------

	public void addItemInZone(int i) {
		this.nbItemInZone += i;
	}

	@Override
	public void active() {
		if (!isDisable) {
			this.isHovered = true;
			this.machine.hoverZone(+1); 
		}

	}

	@Override
	public void stop() {
		if (!isDisable) {
			this.isHovered = false;
			this.machine.hoverZone(-1); 
		}
	}

	@Override
	public void destroy() {
		isDisable = true;
		
		System.out.println("Je suis detruit");
	}

	// -------------

	/**
	 * Retourne
	 * 
	 * @return the isHovered
	 */
	public boolean isHovered() { return isHovered; }

	/**
	 * Retourne
	 * 
	 * @return the isDisable
	 */
	public boolean isDisable() { return isDisable; }

	/**
	 * Retourne
	 * 
	 * @return the color
	 */
	public DodgeColor getColor() { return color; }

	/**
	 * Retourne
	 * 
	 * @return the points
	 */
	public List<Double> getPoints() { return points; }

	/**
	 * Retourne
	 * 
	 * @return the position
	 */
	public Position getPosition() { return position; }
	
	

	/** 
	 * Retourne
	 * @return the size
	 */
	public double getSize() { return size; }

	@Override
	public String toString() {
		return "Zone [points=" + points + " size : " + size + " Color : " + color;
	}
	
	/** 
	 * Retourne
	 * @return the nbItemInZone
	 */
	public int getNbItemInZone() { return nbItemInZone; }

}