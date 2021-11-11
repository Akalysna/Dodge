package node;

import app.DodgeCtrl;
import javafx.animation.AnimationTimer;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import util.KeyTouch;
import util.KeyTouch.MoveDirection;

public class Cuby extends Rectangle {

	private double x;
	private double y;
	private double vitesse;

	private int taille;

	private Color color;

	private KeyTouch keyTouch;
	private AnimationTimer animTimer;
	

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

	public Cuby(Color color, KeyTouch kt) {
		this(0, 0, color, 15,5, kt);
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

				if (keyTouch.isPressed(MoveDirection.UP) && (getY() > 0)) {
					setY(getY() - vitesse);
				}

				if (keyTouch.isPressed(MoveDirection.RIGHT) && (getX() < DodgeCtrl.SCENE_WIDTH - taille)) {
					setX(getX() + vitesse);
				}

				if (keyTouch.isPressed(MoveDirection.DOWN) && (getY() < DodgeCtrl.SCENE_HEIGHT -taille)) {
					setY(getY() + vitesse);
				}

				if (keyTouch.isPressed(MoveDirection.LEFT) && (getX() > 0)) {
					setX(getX() - vitesse);
				}
			}
		};
	}
	
	
	public void move(KeyEvent event, boolean b) {
		for (MoveDirection md : MoveDirection.values()) {
			if (event.getCode().equals(keyTouch.getKeyCode(md))) {
				keyTouch.setPressed(md, b);
			}
		}
	}

}
