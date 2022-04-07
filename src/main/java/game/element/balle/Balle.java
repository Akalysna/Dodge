package game.element.balle;

import controler.DataCtrl;
import game.element.factory.BallFactory.TypeBalle;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import util.StatObject;

/**
 * 
 * 
 * @author llona André--Augustine
 * @version 1.0
 *
 */
public abstract class Balle extends Circle {

	protected double x;
	protected double y;

	protected int taille;
	protected Color color;
	protected double vitesse;

	protected TypeBalle typeBalle;
	protected AnimationTimer animBall;

	protected StatObject<Integer> life;
	protected boolean isDestroy;
	
	protected double dx;
	protected double dy;

	// ---------------------
	// Constructeur
	// ---------------------

	/**
	 * Constructeur de Balle
	 * 
	 * @param color   Couleur de la balle
	 * @param x       Position en x de la balle (Depuis le centre)
	 * @param y       Position en y de la balle (Depuis le centre)
	 * @param rayon   Rayon de la balle
	 * @param vitesse Vitesse de déplacement de la balle
	 * @param type    Type de la balle
	 * @param life    Durée de vie de la balle en terme de rebond
	 */
	protected Balle(double x, double y, int rayon, double vitesse, TypeBalle type, int life) {

		this.typeBalle = type;
		this.vitesse = vitesse;
		this.taille = rayon;
		this.x = x;
		this.y = y;
		
		this.dx = DataCtrl.negNb(Math.cos(45)) * vitesse;
		this.dy  = DataCtrl.negNb(Math.sin(45)) * vitesse;

		this.color = Color.WHITE;
		this.life = new StatObject<>(life);

		init();
		initAnimBall();
		this.isDestroy = false;

	}

	protected Balle(double x, double y, int rayon, double vitesse, TypeBalle type) {
		this(x, y, rayon, vitesse, type, 10);
	}

	// ---------------------
	// Méthode
	// ---------------------

	/**
	 * Initialise la balle en lui donnant des attributs en fonction du constructeur
	 */
	private void init() {

		this.setFill(color);
		this.setCenterX(x);
		this.setCenterY(y);
		this.setRadius(taille);
	}

	/**
	 * Initialise l'animation de la balle
	 */
	protected abstract void initAnimBall();

	/**
	 * Anime la balle. </br>
	 * Vrai pour animer et faux pour stopper l'animation
	 * 
	 * @param b Boolean
	 */
	public void animateBall(boolean b) {

		if (b)
			animBall.start();
		else
			animBall.stop();
	}

	protected void destroy() {

		animateBall(false);
		this.setStroke(Color.WHITE);
		this.setStrokeWidth(2);
		this.getStrokeDashArray().addAll(5.0, 2.0);
		this.setFill(Color.TRANSPARENT);
		new Timeline(new KeyFrame(Duration.millis(300), e -> isDestroy = true, 
				new KeyValue(this.scaleXProperty(), 1.8),
				new KeyValue(this.scaleYProperty(), 1.8), 
				new KeyValue(this.strokeProperty(), Color.TRANSPARENT),
				new KeyValue(this.strokeWidthProperty(), 0))).play();
	}



	// ---------------------
	// Accesseur et Mutateur
	// ---------------------


	public double getVitesse() { return vitesse; }

	public int getTaille() { return taille; }

	public double getX() { return x; }

	public double getY() { return y; }

	public AnimationTimer getAnimBall() { return animBall; }

	public TypeBalle getTypeBalle() { return typeBalle; }

	public Color getColor() { return color; }

	public void setVitesse(int vitesse) { this.vitesse = vitesse; }

	public void setTaille(int taille) { this.taille = taille; }

	public void setX(int x) { this.x = x; }

	public void setY(int y) { this.y = y; }

	public void setAnimBall(AnimationTimer animBall) { this.animBall = animBall; }

	public void setTypeBalle(TypeBalle typeBalle) { this.typeBalle = typeBalle; }

	public void setColor(Color color) { this.color = color; }

	public StatObject<Integer> getLife() { return life; }

	public boolean isDestroy() { return isDestroy; }

	public void setDestroy(boolean isDestroy) { this.isDestroy = isDestroy; }

}
