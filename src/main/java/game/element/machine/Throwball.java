/**
 * 
 */
package game.element.machine;

import java.util.ArrayList;

import controller.DataCtrl.DodgeShape;
import controller.DataCtrl.TypeElement;
import util.Position;
import util.Stats;

/**
 * @author Llona André--Augustine
 *
 */
public class Throwball {
	
	/**Position de la machine*/
	private Position position; 
	
	/**Forme de la machine*/
	private DodgeShape shape; 
	
	/**Point de vie de la machine*/
	private Stats<Integer> life; 
	
	/**Intervalle de temps entre chaque lancé de balle (milli)*/
	private int throwDelay; 
	
	/**Type des balles lancé par la machine*/
	private ArrayList<TypeElement> typeBalls;
	

	/**
	 * @param position
	 * @param shape
	 * @param life
	 * @param throwDelay Intervalle de temps entre chaque lancé de balle (milli)
	 * @param typeBalls
	 */
	public Throwball(Position position, DodgeShape shape, int life, int throwDelay,TypeElement... typeBall) {
		this.position = position;
		this.shape = shape;
		this.life = new Stats<>(life);
		this.throwDelay = throwDelay;
		this.typeBalls = new ArrayList<>(typeBalls);
	} 

}
