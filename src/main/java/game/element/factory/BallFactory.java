package game.element.factory;

import controler.DataCtrl.TypeElement;
import game.element.balle.Ball;
import game.element.balle.FocusBall;
import game.element.balle.GhostBall;
import game.element.balle.InvertBall;
import game.element.balle.SimpleBall;
import util.Position;

public class BallFactory {

	public enum TypeBalle {

		FIRE, SIMPLE, BOMB, GHOST, INVERT;
	}

	public static Ball get(TypeElement tm, Position pos) {
		switch (tm) {
		case SIMPLE:
			return simpleBalle(pos);
//		case FIRE:
//			return focusBalle(x, y);
//		case BOOM:
//
//			return null;
//		case GHOST:
//			return ghostBalle(x, y);
//			
//		case INVERT:
//			return invertBalle(x, y);

		default:
			return null;
		}
	}

	public static Ball simpleBalle(Position pos) {
		return new SimpleBall(pos, 8);
	}
	
//	public static Ball ghostBalle(double x, double y) {
//		return new GhostBall(x, y, 8, 8);
//	}
//	
//	public static Ball invertBalle(double x, double y) {
//		return new InvertBall(x, y, 8, 8, true);
//	}
//	
//	public static Ball focusBalle(double x, double y) {
//		return new FocusBall(x, y, 8, 8);
//	}


}
