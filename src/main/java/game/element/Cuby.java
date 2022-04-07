/**
 * 
 */
package game.element;

import controller.DataCtrl.DodgeColor;
import controller.DataCtrl.DodgeShape;
import util.Position;

/**
 * @author Llona André--Augustine
 *
 */
public class Cuby {
	
	private Position position; 
	private DodgeColor color; 
	
	private double speed; 
	
	private double size;
	

	/**
	 * @param position
	 * @param color
	 * @param speed
	 * @param size
	 */
	public Cuby(Position position, DodgeColor color, double speed, double size) {
		
		this.position = position;
		this.color = color;
		this.speed = speed;
		this.size = size;
	}
	
	/**
	 * @param position
	 * @param color
	 * @param speed
	 * @param size
	 */
	public Cuby(DodgeColor color) {
		
		this.position = new Position(0, 0);
		this.color = color;
		this.speed = 10;
		this.size = 8;
	}
	
	/**
	 * 
	 */
	public void moveToX(int i) {
		this.position.addCoordinate(speed*i, 0);
	}
	
	public void moveToY(int i) {
		this.position.addCoordinate(0, speed*i);
	}

	/** 
	 * Retourne
	 * @return the position
	 */
	public Position getPosition() { return position; }

	/** 
	 * Retourne
	 * @return the color
	 */
	public DodgeColor getColor() { return color; }

	/** 
	 * Retourne
	 * @return the speed
	 */
	public double getSpeed() { return speed; } 
	
	

}
