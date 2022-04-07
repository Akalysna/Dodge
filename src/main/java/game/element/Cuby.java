package game.element;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import util.KeyTouch;

/**
 * Classe représentant le cube controlé par le joueur
 * 
 * @author Llona André--Augustine
 */
public class Cuby extends Rectangle {

	private double vitesse;
	private int taille;
	private Color color;

	private double x; 
	private double y; 

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
	public Cuby(double x, double y, Color color, int width, double vitesse, KeyTouch kt) {

		this.vitesse = vitesse;
		this.color = color;
		this.taille = width;
		
		this.x = x; 
		this.y = y;

		init();
	}
	
	/**
	 * Constructeur de cuby
	 * @param color
	 */
	public Cuby(Color color) {

		this.vitesse = 6;
		this.color = color;
		this.taille = 15;
		
		this.x = 0; 
		this.y = 0;

		init();
	}

	/***
	 * Constructeur du cuby
	 * <p>
	 * La position en x et y est par defaut a 0. La taille a 15 et la vitesse à 6
	 * </p>
	 * 
	 * @param color Couleur du cuby
	 * @param kt    Touche directionnel associé
	 */
	public Cuby(Color color, KeyTouch kt) {
		this(0, 0, color, 15, 6, kt);
	}


	private void init() {

		this.setWidth(taille);
		this.setHeight(taille);

		this.setX(x);
		this.setY(y);

		this.setFill(color);

	}
	
	/**
	 * Cahnge les coordonné du cuby
	 * @param x
	 * @param y
	 */
	public void move(double x, double y) {
		
		this.x += x*vitesse; 
		this.y += y*vitesse; 
		
		this.setLayoutX(this.x);
		this.setLayoutY(this.y);
	}

}
