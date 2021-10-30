package node.machine;

import ctrl.ControleurDonnee;

public class MachineFactory {

	public enum TypeMachine {
		SIMPLE, SNAKE, SIZE;
	}

	public Machine get(TypeMachine tm, int x, int y) {
		switch (tm) {
		case SIMPLE:
			return simpleMachine(x, y); 
		case SNAKE:

			return null;
		case SIZE:

		return null;

		default:
			return null; 
		}
	}

	public Machine simpleMachine(int x, int y) {
		return new Machine(x, y, 30, 0, ControleurDonnee.randomColor());
	}


}
