package game.element;

import controler.DataCtrl.DodgeColor;
import util.Position;

/**
 * Classe représentant le cube controlé par le joueur
 * 
 * @author Llona André--Augustine
 */
public class Cuby{

	private double speed;
	private double size;
	private DodgeColor color;

	private Position position;  

	/**
	 * Constructeur du cuby
	 * 
	 * @param x       Position en X du Cuby
	 * @param y       Position en Y du Cuby
	 * @param color   Couleur du cuby
	 * @param width   Largeur du cuby (Largeur = Longueur)
	 * @param vitesse Vitesse de déplacement du cuby
	 * @param kt      Touche directionnel associé
	 */
	public Cuby(Position pos, DodgeColor color, int width, double vitesse) {

		this.speed = vitesse;
		this.color = color;
		this.size = width;
		
		this.position = pos; 

	}
	
	/**
	 * 
	 * @param pos
	 * @param color
	 */
	public Cuby(Position pos,DodgeColor color) {
		this(pos, color, 15, 6); 
	}
	
	/**
	 * 
	 * @param color
	 */
	public Cuby(DodgeColor color) {
		this(new Position(0, 0), color, 15, 6); 
	}

	//--------------------
	
	/**
	 * Cahnge les coordonné du cuby
	 * @param dx
	 * @param dy
	 */
	public void move(double dx, double dy) {
		this.position.addCoordinate(dx*speed, dy*speed);
	}

	//--------------------
	
	/** 
	 * Retourne
	 * @return the speed
	 */
	public double getSpeed() { return speed; }

	/** 
	 * Retourne
	 * @return the size
	 */
	public double getSize() { return size; }

	/** 
	 * Retourne
	 * @return the color
	 */
	public DodgeColor getColor() { return color; }

	/** 
	 * Retourne
	 * @return the position
	 */
	public Position getPosition() { return position; }
	
	

	
	
}
