package game.element.factory;

import game.element.balle.Ball;
import game.element.balle.FocusBall;
import game.element.balle.GhostBall;
import game.element.balle.InvertBall;
import game.element.balle.SimpleBall;

public class BallFactory {

	public enum TypeBalle {

		FIRE, SIMPLE, BOMB, GHOST, INVERT;
	}

	public static Ball get(TypeBalle tm, double x, double y) {
		switch (tm) {
		case SIMPLE:
			return simpleBalle(x, y);
		case FIRE:
			return focusBalle(x, y);
		case BOMB:

			return null;
		case GHOST:
			return ghostBalle(x, y);
			
		case INVERT:
			return invertBalle(x, y);

		default:
			return null;
		}
	}

	public static Ball simpleBalle(double x, double y) {
		return new SimpleBall(x, y, 8, 8);
	}
	
	public static Ball ghostBalle(double x, double y) {
		return new GhostBall(x, y, 8, 8);
	}
	
	public static Ball invertBalle(double x, double y) {
		return new InvertBall(x, y, 8, 8, true);
	}
	
	public static Ball focusBalle(double x, double y) {
		return new FocusBall(x, y, 8, 8);
	}


}
