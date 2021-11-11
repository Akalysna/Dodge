package game.niveau;

import java.util.ArrayList;

import game.element.machine.Machine;
import game.element.zone.Zone;

public class Stage {

	private int maxMachineDestroy;

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

	public void setMachineDestroy() {
		this.maxMachineDestroy++;
	}

	public int getNbMachine() { return machines.size();}

	public int getMaxMachineDestroy() { return maxMachineDestroy; }
	

}
