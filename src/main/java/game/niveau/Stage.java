package game.niveau;

import java.util.ArrayList;
import java.util.List;

import game.element.machine.Machine;
import util.Position;

public class Stage {

	private ArrayList<Machine> machines;
	private Position cubyPos;

	public Stage(Position cubyPos) {
		this.machines = new ArrayList<>();
		this.cubyPos = cubyPos;
	}

	public void addMachine(Machine machine) {
		this.machines.add(machine);
	}

	public List<Machine> getMachines() { return machines; }

	/**
	 * Retourne
	 * 
	 * @return the cubyPos
	 */
	public Position getCubyPos() { return cubyPos; }

	@Override
	public String toString() {
		return "Stage [machines=" + machines + ", cubyPos=" + cubyPos + "]";
	}


}
