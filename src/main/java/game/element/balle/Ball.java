/**
 * 
 */
package game.element.balle;

import controller.DataCtrl;
import controller.DataCtrl.DodgeColor;
import controller.DataCtrl.DodgeShape;
import javafx.animation.AnimationTimer;
import javafx.animation.Timeline;
import util.Position;
import util.RandomUtil;
import util.Stats;

/**
 * @author Llona André--Augustine
 *
 */
public abstract class Ball {

	protected Position position;
	protected DodgeColor color;
	protected DodgeShape shape;

	protected double radius;
	protected double speed; 
	
	/**Nombre de rebond avant que la balle ne disparraisse*/
	protected Stats<Integer> life; 

	protected boolean isStop; 
	
	/**Indique si la balle à disparu*/
	protected boolean hasDisappeared; 
	
	protected AnimationTimer mouvementTimeline;
	
	protected double dx;
	protected double dy;

	// -----------------

	/**
	 * @param position
	 * @param color
	 * @param size
	 */
 	protected Ball(Position position, DodgeColor color, double size, DodgeShape shape, int life) {
		this.position = position;
		this.color = color;
		this.radius = size;
		this.shape = shape; 
		this.isStop = true;
		this.speed = 5; 
		this.life = new Stats<>(life);
		
		this.dx = RandomUtil.invertSign(Math.cos(45) * speed);
		this.dy  = RandomUtil.invertSign(Math.sin(45) * speed);
	}

	/**
	 * @param position
	 * @param color
	 * @param radius
	 */
	protected Ball(Position position, DodgeShape shape) {
		this(position, DodgeColor.WHITE, 4, shape, 8);
	}

	// ------------------

	/**
	 * @param position the position to set
	 */
	public void moveTo(Position position) {
		this.position = position;
	}

	
	protected abstract void ballMouvements(); 
	
	
	protected void changeLifePoint(int i) {
		life.setCurrent(life.getCurrent() + i);
	}
	
	// ------------------


	/**
	 * Retourne
	 * 
	 * @return the position
	 */
	public Position getPosition() { return position; }

	/**
	 * Retourne
	 * 
	 * @return the color
	 */
	public DodgeColor getColor() { return color; }

	/**
	 * Retourne
	 * 
	 * @return the radius
	 */
	public double getRadius() { return radius; }
	
	/** 
	 * Retourne
	 * @return the shape
	 */
	public DodgeShape getShape() { return shape; }
	
	/** 
	 * Retourne
	 * @return the isStop
	 */
	public boolean isStop() { return isStop; }
	
	/** 
	 * Retourne
	 * @return the speed
	 */
	public double getSpeed() { return speed; }
	
	//-----

	/**
	 * @param color the color to set
	 */
	public void setColor(DodgeColor color) { this.color = color; }

	/**
	 * @param radius the radius to set
	 */
	public void setRadius(double radius) { this.radius = radius; }
	
	/**
	 * @param shape the shape to set
	 */
	public void setShape(DodgeShape shape) { this.shape = shape; }
	
	/**
	 * @param isStop the isStop to set
	 */
	public void setStop(boolean isStop) { this.isStop = isStop; }
	
	/**
	 * @param speed the speed to set
	 */
	public void setSpeed(double speed) { this.speed = speed; }

}
