package game.element;

import controller.DodgeCtrl;
import javafx.animation.AnimationTimer;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import util.KeyTouch;
import util.KeyTouch.MoveDirection;

/**
 * Classe représentant le cube controlé par le joueur
 * 
 * @author Llona
 */
public class Cuby extends Rectangle {

	private double x;
	private double y;
	private double vitesse;

	private int taille;

	private Color color;

	private KeyTouch keyTouch;
	private AnimationTimer animTimer;


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

		this.x = x;
		this.y = y;
		this.vitesse = vitesse;
		this.color = color;
		this.taille = width;

		this.keyTouch = kt;

		init();
		anim();
		animTimer.start();
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

	private void anim() {

		animTimer = new AnimationTimer() {


			@Override
			public void handle(long now) {

				if (keyTouch.isPressed(MoveDirection.UP) && (getY() > 0))
					setY(getY() - vitesse);


				if (keyTouch.isPressed(MoveDirection.RIGHT) && (getX() < DodgeCtrl.SCENE_WIDTH - taille))
					setX(getX() + vitesse);


				if (keyTouch.isPressed(MoveDirection.DOWN) && (getY() < DodgeCtrl.SCENE_HEIGHT - taille))
					setY(getY() + vitesse);


				if (keyTouch.isPressed(MoveDirection.LEFT) && (getX() > 0))
					setX(getX() - vitesse);

			}
		};
	}

	/**
	 * 
	 * @param keyCode Code la touche
	 * @param b       true : la touche est pressé <br>
	 *                false : la touche est relaché
	 */
	public void move(KeyCode keyCode, boolean b) {

		// Pour chaque direction
		for (MoveDirection md : MoveDirection.values()) {

			// Si touche passé en parametre est egale la touche associé a la direction
			if (keyCode.equals(keyTouch.getKeyCode(md))) {
				keyTouch.setPressed(md, b);
			}
		}
	}

}
