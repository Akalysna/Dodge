package factory;

import node.balle.Balle;
import node.balle.SimpleBall;

public class BallFactory {

	public enum TypeBalle {

		FIRE, SIMPLE, BOMB, GHOST;
	}

	public static Balle get(TypeBalle tm, double x, double y) {
		switch (tm) {
		case SIMPLE:
			return simpleBalle(x, y);
		case FIRE:

			return null;
		case BOMB:

			return null;
		case GHOST:

			return null;

		default:
			return null;
		}
	}

	public static Balle simpleBalle(double x, double y) {
		return new SimpleBall(x, y, 8, 5.0);
	}


}
