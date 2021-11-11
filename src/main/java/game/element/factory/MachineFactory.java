package game.element.factory;

import java.util.Arrays;

import game.element.factory.BallFactory.TypeBalle;
import game.element.machine.Machine;
import javafx.scene.paint.Color;

public class MachineFactory {

	public enum TypeMachine {
		SIMPLE, SNAKE, SIZE, GHOST, INVERT,MOVE;
	}

	public static Machine get(TypeMachine tm, int x, int y, Color color) {
		switch (tm) {
		case SIMPLE:
			return simpleMachine(x, y, color);
		case SNAKE:

			return null;
			
		case SIZE:
			return null;

		case GHOST:
			return simpleMachineGhost(x, y, color);
			
		case INVERT:
			return simpleMachineInvert(x, y, color);
			
		default:
			return null;
		}
	}

	public static Machine simpleMachine(int x, int y, Color color) {
		return new Machine(x, y, 50, 5, color, Arrays.asList(TypeBalle.SIMPLE));
	}

	public static Machine simpleMachineGhost(int x, int y, Color color) {
		return new Machine(x, y, 50, 5, color, Arrays.asList(TypeBalle.GHOST));
	}
	
	public static Machine simpleMachineInvert(int x, int y, Color color) {
		return new Machine(x, y, 50, 5, color, Arrays.asList(TypeBalle.INVERT));
	}


}
