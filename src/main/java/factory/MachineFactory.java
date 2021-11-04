package factory;

import java.util.Arrays;

import factory.BallFactory.TypeBalle;
import javafx.scene.paint.Color;
import node.machine.Machine;

public class MachineFactory {

	public enum TypeMachine {
		SIMPLE, SNAKE, SIZE;
	}

	public static Machine get(TypeMachine tm, int x, int y, Color color) {
		switch (tm) {
		case SIMPLE:
			return simpleMachine(x, y, color); 
		case SNAKE:

			return null;
		case SIZE:

		return null;

		default:
			return null; 
		}
	}

	public static Machine simpleMachine(int x, int y, Color color) {
		return new Machine(x, y, 50, 5, color, Arrays.asList(TypeBalle.SIMPLE));
	}


}
