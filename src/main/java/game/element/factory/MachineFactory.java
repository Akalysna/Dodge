package game.element.factory;

import java.util.Arrays;

import controler.DataCtrl.DodgeColor;
import controler.DataCtrl.DodgeShape;
import controler.DataCtrl.TypeElement;
import game.element.machine.Throwball;
import util.Position;

public class MachineFactory {

	public enum TypeMachine {
		SIMPLE, SNAKE, SIZE, GHOST, INVERT,MOVE;
	}

	public static Throwball get(TypeElement type, Position pos) {
		switch (type) {
		case SIMPLE:
			return simpleMachine(pos);
			
		default:
			return null;
		}
	}

	public static Throwball simpleMachine(Position pos) {
		return new Throwball(pos, 5, DodgeColor.PINK, DodgeShape.ROUND, Arrays.asList(TypeElement.SIMPLE));
	}


}
