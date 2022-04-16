package game.element;

import app.Dodge;
import controler.DataCtrl.DodgeColor;
import javafx.beans.property.DoubleProperty;
import util.Position;

/**
 * Classe représentant le cube controlé par le joueur
 * 
 * @author Llona André--Augustine
 */
public class Cuby  implements Element{

	private double speed;
	private double size;
	private DodgeColor color;

	private Position position;
	private boolean isMoving; 

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
		this.isMoving = false; 
	}

	/**
	 * 
	 * @param pos
	 * @param color
	 */
	public Cuby(Position pos, DodgeColor color) {
		this(pos, color, 15, 6);
	}

	/**
	 * 
	 * @param color
	 */
	public Cuby(DodgeColor color) {
		this(new Position(0, 0), color, 15, 6);
	}

	// --------------------

	/**
	 * Change les coordonnés du cuby
	 * 
	 * @param dx
	 * @param dy
	 */
	public void move(double dx, double dy) {

		//System.out.println("move in cuby class : " + this.position);
		
		if (dx > 0 && this.position.getX() < Dodge.SCENE_WIDTH - size) {
			this.position.addX(dx * speed);
		}

		if (dx < 0 && this.position.getX() > 0) {
			this.position.addX(dx * speed);
		}

		if (dy > 0 && this.position.getY() < Dodge.SCENE_HEIGHT - size) {
			this.position.addY(dy * speed);
		}

		if (dy < 0 && this.position.getY() > 0) {
			this.position.addY(dy * speed);
		}
	}
	
	@Override
	public void active() {
		this.isMoving = true;
		
	}

	@Override
	public void stop() {
		this.isMoving = false; 
	}

	@Override
	public void destroy() {
		stop();
	}

	// --------------------

	/**
	 * Retourne
	 * 
	 * @return the speed
	 */
	public double getSpeed() { return speed; }

	/**
	 * Retourne
	 * 
	 * @return the size
	 */
	public double getSize() { return size; }

	/**
	 * Retourne
	 * 
	 * @return the color
	 */
	public DodgeColor getColor() { return color; }

	/**
	 * Retourne
	 * 
	 * @return the position
	 */
	public Position getPosition() { return position; }
	

	/**
	 * @param position the position to set
	 */
	public void setPosition(Position position) { this.position = position; }

	/** 
	 * Retourne
	 * @return the isMoving
	 */
	public boolean isMoving() { return isMoving; }

}
