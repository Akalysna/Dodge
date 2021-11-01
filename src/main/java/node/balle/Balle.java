package node.balle;

import factory.BallFactory.TypeBalle;
import javafx.animation.AnimationTimer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * 
 * 
 * @author llona André--Augustine
 * @version 1.0
 *
 */
public abstract class Balle extends Circle {

	protected int vitesse;
	protected int taille;

	protected int x;
	protected int y;

	protected Color color;

	protected AnimationTimer animBall;

	protected TypeBalle typeBalle;

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
	 */
	protected Balle(int x, int y, int rayon, int vitesse, TypeBalle type) {

		this.typeBalle = type;
		this.vitesse = vitesse;
		this.taille = rayon;
		this.x = x;
		this.y = y;

		this.color = Color.GRAY;

		init();
		initAnimBall();

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
	public abstract void animateBall(Boolean b);

	/**
	 * Transforme un nombre en nombre négatif de façon aléatoire
	 * 
	 * @param nb Nombre à transformer
	 * @return int
	 */
	protected int negNb(int nb) {
		return (Math.random() * 10) > 5 ? -nb : nb;
	}

	// ---------------------
	// Accesseur et Mutateur
	// ---------------------


	public int getVitesse() { return vitesse; }
	public int getTaille() { return taille; }

	public int getX() { return x; }
	public int getY() { return y; }

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

}
