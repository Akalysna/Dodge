package game.element.machine;

import java.util.List;

import controler.DataCtrl.DodgeColor;
import controler.DataCtrl.DodgeShape;
import controler.DataCtrl.TypeElement;
import event.EventRegister;
import exception.EmptyListException;
import game.element.Element;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import util.Position;
import util.RandomUtil;
import util.Stats;

public class Machine implements Element {

	// ------------------------------------
	// Attribut
	// ------------------------------------

	private Stats<Integer> life;

	// private boolean isThrowBall;

	private boolean isDestroy;
	private boolean isActived;

	/** Délai entre chaque lancé de balle exprimé en miliseconde */
	private int delayThrow;
	
	/***
	 * Lorsque la machine est détruite toutes les zone doivents l'etre 
	 * Quand une zon est survoler le décompte de la machine dois descendre
	 */

	// -----

	private DodgeColor color;
	private DodgeShape shape;
	private Position position;
	private List<TypeElement> ballType;

	private double radius;

	// Chrono
	private Timeline timelineChrono;
	private Timeline timelineBall;


	// ------------------------------------
	// Constructeur
	// ------------------------------------

	/**
	 * Constructeur d'une machine simple
	 * 
	 * @param x         Position en X
	 * @param y         Position en Y
	 * @param taille    Taille de la machine
	 * @param lifePoint Point de vie de la machine
	 * @param color     Couleur de la machine
	 * @param typeBalle Les types de balles que la machine lance
	 */
	public Machine(Position pos, int life, DodgeColor color, DodgeShape shape, List<TypeElement> typeBalle) {
		this(pos, 10, life, color, shape, 3, typeBalle);
	}

	/**
	 * 
	 * @param x                    Position en X
	 * @param y                    Position en Y
	 * @param taille               Taille de la machine
	 * @param lifePoint            Point de vie de la machine
	 * @param color                Couleur de la machine
	 * @param delayThrow           Delai entre chaque lancer
	 * @param speedLifePointChrono Vitesse du decompte des points de vie
	 */
	protected Machine(Position pos, int radius, int life, DodgeColor color, DodgeShape shape, int delayThrow,
			List<TypeElement> typeBalle) {

		this.position = pos;
		this.radius = radius;

		this.life = new Stats<>(life);
		this.color = color;
		this.shape = shape;

		this.delayThrow = delayThrow;
		this.ballType = typeBalle;

		this.isActived = false;
		this.isDestroy = false;

		throwBall();
		lifePoint();

		timelineChrono.setCycleCount(Animation.INDEFINITE);
		timelineBall.setCycleCount(Animation.INDEFINITE);
	}

	// ------------------------------------


	/**
	 * Initialisation du chronomètre permettant de lancer les balles de la machine
	 */
	private void throwBall() {

		timelineBall = new Timeline(new KeyFrame(Duration.millis(this.delayThrow), ev -> {

			try {

				@SuppressWarnings("unused")
				TypeElement type = (TypeElement) RandomUtil.getRandomElement(ballType);

				// TODO Evenenement pour lancer une balle
				// DataCtrl.GAME_EVENT.handle(new ThrowEvent(type, position));

			} catch (EmptyListException e) {
				e.printStackTrace();
			}

		}));
	}

	/** Initialisation du chronomètre des points de vie de la machine */
	private void lifePoint() {

		// Chrono de la machine
		timelineChrono = new Timeline(new KeyFrame(Duration.seconds(1), ev -> {

			changeLifePoint(-1);

			if (life.getCurrent() <= 0)
				destroy();
		}));
	}

	protected void changeLifePoint(int i) {
		life.setCurrent(life.getCurrent() + i);
	}

	// ----

	/**
	 * Détruit la machine. Stop l'animation de rotation, le chrono et le lancer de
	 * balle
	 */
	@Override
	public void destroy() {

		this.timelineBall.stop();
		this.timelineChrono.stop();

		this.isDestroy = true;
		this.life.reset();
	}

	@Override
	public void active() {
		if (!isDestroy)
			timelineChrono.play();
	}

	@Override
	public void stop() {

		if (!isDestroy) {
			timelineChrono.stop();
			this.life.reset();
		}
	}

	// ------------------------------------
	// Accesseur et Mutateur
	// ------------------------------------


	/**
	 * Retourne
	 * 
	 * @return the life
	 */
	public int getLife() { return life.getCurrent(); }

	/**
	 * Retourne
	 * 
	 * @return the isDestroy
	 */
	public boolean isDestroy() { return isDestroy; }

	/**
	 * Retourne
	 * 
	 * @return the isActived
	 */
	public boolean isActived() { return isActived; }

	/**
	 * Retourne
	 * 
	 * @return the color
	 */
	public DodgeColor getColor() { return color; }

	/**
	 * Retourne
	 * 
	 * @return the shape
	 */
	public DodgeShape getShape() { return shape; }

	/**
	 * Retourne
	 * 
	 * @return the position
	 */
	public Position getPosition() { return position; }

	/**
	 * Retourne
	 * 
	 * @return the radius
	 */
	public double getRadius() { return radius; }

	/**
	 * Retourne la liste des balles pouvant être lancé par la machine
	 * 
	 * @return the ballType
	 */
	public List<TypeElement> getBallType() { return ballType; }

}
