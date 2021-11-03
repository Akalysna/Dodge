package game.niveau;

import java.util.ArrayList;

import node.machine.Machine;
import node.zone.Zone;

public class Stage {

	private ArrayList<Machine> machines;
	private ArrayList<Zone> zones;

	public Stage() {
		this.machines = new ArrayList<>();
		this.zones = new ArrayList<>();
	}

	public void addMachine(Machine m) {
		this.machines.add(m);
	}

	public void addZone(Zone z) {
		this.zones.add(z);
	}

	public ArrayList<Machine> getMachines() { return machines; }

	public ArrayList<Zone> getZones() { return zones; }
	
	
}
