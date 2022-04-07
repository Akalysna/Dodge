/**
 * 
 */
package game.element.zone;

import java.util.ArrayList;
import java.util.List;

import controller.DataCtrl.DodgeColor;
import util.Position;

/**
 * @author Llona André--Augustine
 *
 */
public class Zone {
	
	private ArrayList<Double> formes;
	private double size; 
	
	private Position position; 
	private DodgeColor color; 
	
	private boolean isHovered;
	private boolean isDisable; 
	
	
	
	
	public List<Double> getFormePoint() {
		
		ArrayList<Double> list = new ArrayList<>(); 
		
		for (Double d : formes) {
			list.add(d*size); 
		}
		
		return list; 

	}

	/** 
	 * Retourne
	 * @return the size
	 */
	public double getSize() { return size; }

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
	 * @return the isHovered
	 */
	public boolean isHovered() { return isHovered; }

	/** 
	 * Retourne
	 * @return the isDisable
	 */
	public boolean isDisable() { return isDisable; }

	

}
