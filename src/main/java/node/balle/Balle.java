package node.balle;

import javafx.animation.AnimationTimer;
import javafx.animation.Timeline;
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

	// ---------------------
	// Attribut
	// ---------------------

	/**
	 * Vitesse de la balle
	 */
	protected int vitesse;

	/**
	 * Taille de la balle en rayon
	 */
	protected int taille;

	/**
	 * Position de la balle en x depuis le centre
	 */
	protected int x;

	/**
	 * Position de la balle en y depuis le centre
	 */
	protected int y;

	/**
	 * Couleur de la balle
	 */
	protected Color color;

	/**
	 * Animation de la balle
	 */
	protected AnimationTimer animBall;

	/**
	 * Type de la balle
	 */
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
	public Balle(int x, int y, int rayon, int vitesse, TypeBalle type) {

		this.typeBalle = type;
		this.vitesse = vitesse;
		this.taille = rayon;
		this.x = x;
		this.y = y;

		this.color = Color.WHITE;

		initBalle();
		initAnimBall();

	}

	// ---------------------
	// Méthode
	// ---------------------

	/**
	 * Initialise la balle en lui donnant des attributs en fonction du constructeur
	 */
	private void initBalle() {

		this.setFill(Color.WHITE);
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

		int i =(int) (Math.random() * 10);
		System.out.println("i : " + i);

		if (i > 5)
			return -nb;
		else
			return nb;

	}

	// ---------------------
	// Accesseur et Mutateur
	// ---------------------

	/**
	 * Retourne la vitesse de la balle
	 * 
	 * @return int
	 */
	public int getVitesse() {
		return vitesse;
	}

	/**
	 * Retourne le rayon de la balle
	 * 
	 * @return int
	 */
	public int getTaille() {
		return taille;
	}

	/**
	 * Retourne la position en x de la balle
	 * 
	 * @return int
	 */
	public int getX() {
		return x;
	}

	/**
	 * Retourne la position en y de la balle
	 * 
	 * @return int
	 */
	public int getY() {
		return y;
	}

	/**
	 * Retourne la couleur de la balle
	 * 
	 * @return Color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Met à jour la vitesse de la balle
	 * 
	 * @param vitesse Vitesse de la balle
	 */
	public void setVitesse(int vitesse) {
		this.vitesse = vitesse;
	}

	/**
	 * Met à jour le rayon de la balle
	 * 
	 * @param taille Rayon de la balle
	 */
	public void setTaille(int taille) {
		this.taille = taille;
	}

	/**
	 * Met à jour la position en x de la balle
	 * 
	 * @param x Position en x
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Met à jour la position en y de la balle
	 * 
	 * @param y Position en y
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * Met à jour la couleur de la balle
	 * 
	 * @param color Couleur de la balle
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	public TypeBalle getTypeBalle() {
		return typeBalle;
	}

	public void setTypeBalle(TypeBalle typeBalle) {
		this.typeBalle = typeBalle;
	}

}
