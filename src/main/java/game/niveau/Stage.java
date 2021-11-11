package game.niveau;

import java.util.ArrayList;

import game.element.machine.Machine;
import game.element.zone.Zone;
import javafx.animation.PathTransition;

public class Stage {

	private ArrayList<PathTransition> pathTransitions; 
	private ArrayList<Machine> machines;
	private ArrayList<Zone> zones;

	public Stage() {
		this.machines = new ArrayList<>();
		this.zones = new ArrayList<>();
		this.pathTransitions = new ArrayList<>();
	}

	public void addMachine(Machine m) {
		this.machines.add(m);
	}

	public void addZone(Zone z) {
		this.zones.add(z);
	}
	
	public void addpathTransition(PathTransition p) {
		this.pathTransitions.add(p);
	}

	public ArrayList<Machine> getMachines() { return machines; }

	public ArrayList<Zone> getZones() { return zones; }

	public ArrayList<PathTransition> getPathTransitions() { return pathTransitions; }
	
	

}
