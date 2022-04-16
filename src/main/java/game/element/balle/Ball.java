package game.element.balle;

import controler.DataCtrl.DodgeColor;
import controler.DataCtrl.TypeElement;
import game.element.Element;
import javafx.animation.AnimationTimer;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import util.Position;
import util.RandomUtil;
import util.Stats;

/**
 * 
 * 
 * @author llona André--Augustine
 * @version 1.0
 *
 */
public abstract class Ball implements Element {

	protected Position position;
	protected DodgeColor color;
	protected TypeElement type;

	protected double radius;
	protected double speed;

	/** Nombre de rebond avant que la balle ne disparraisse */
	protected Stats<Integer> life;

	protected boolean isStop;

	/** Indique si la balle à disparu */
	protected BooleanProperty hasDisappeared;

	protected AnimationTimer mouvementTimeline;

	protected double dx;
	protected double dy;

	// -----------------

	/**
	 * 
	 * @param position
	 * @param color
	 * @param size
	 * @param shape
	 * @param life
	 */
	protected Ball(Position position, DodgeColor color, double size, TypeElement type, int life) {
		this.position = position;
		this.color = color;
		this.radius = size;
		this.type = type;
		this.isStop = true;
		this.speed = 5;
		this.life = new Stats<>(life);
		
		this.hasDisappeared = new SimpleBooleanProperty(false);

		this.dx = RandomUtil.invertSign(Math.cos(45) * speed);
		this.dy = RandomUtil.invertSign(Math.sin(45) * speed);

		initBallMouvement();
	}

	/**
	 * Par défaut la vitesse de la balle est à 5, le rayon a 4, la couleur à blanc
	 * et la forme a rond
	 * 
	 * @param position
	 * @param shape
	 */
	protected Ball(Position position, TypeElement type, int life) {
		this(position, DodgeColor.WHITE, 8, type, life);
	}

	// ------------------

	protected abstract void initBallMouvement();

	protected void changeLifePoint(int i) {
		life.setCurrent(life.getCurrent() + i);
	}

	/**
	 * Stop le mouvement de la balle
	 */
	@Override
	public void stop() {
		this.isStop = true;
		this.mouvementTimeline.stop();
	}

	/**
	 * Active le mouvement de la balle
	 */
	@Override
	public void active() {
		this.isStop = false;
		this.mouvementTimeline.start();
	}

	@Override
	public void destroy() {
		stop();
	}


	/**
	 * 
	 */
	public void changeDirection(Position pos) {

		double x = pos.getX() - position.getX() - radius;
		double y = pos.getY() - position.getY() - radius;

		// √((x2-x1)²+(y2-y1)²)

		double normX = x / (Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2)));
		double normY = y / (Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2)));

		this.dx = normX * speed;
		this.dy = normY * speed;

	}

	// ------------------



	/**
	 * Retourne
	 * 
	 * @return the position
	 */
	public Position getPosition() { return position; }



	@Override
	public String toString() {
		return "Ball (" + position.getX() + ", " + position.getY() + ")";
	}

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
	 * 
	 * @return the shape
	 */
	public TypeElement getType() { return this.type; }

	/**
	 * Retourne
	 * 
	 * @return the isStop
	 */
	public boolean isStop() { return isStop; }

	/**
	 * Retourne
	 * 
	 * @return the speed
	 */
	public double getSpeed() { return speed; }

	// -----

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
	public void setType(TypeElement type) { this.type = type; }

	/**
	 * @param isStop the isStop to set
	 */
	public void setStop(boolean isStop) { this.isStop = isStop; }

	/**
	 * @param speed the speed to set
	 */
	public void setSpeed(double speed) { this.speed = speed; }

	/** 
	 * Retourne
	 * @return the hasDisappeared
	 */
	public BooleanProperty getHasDisappeared() { return hasDisappeared; }

	
}
