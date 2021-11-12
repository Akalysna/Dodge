package game.niveau;

import java.util.ArrayList;

import game.element.machine.Machine;
import game.element.zone.Zone;
import javafx.animation.PathTransition;
import javafx.geometry.Point2D;

public class Stage {

	private ArrayList<PathTransition> pathTransitions;
	private ArrayList<Machine> machines;
	private ArrayList<Zone> zones;

	private Point2D cubyPosition;

	public Stage() {
		this.machines = new ArrayList<>();
		this.zones = new ArrayList<>();
		this.pathTransitions = new ArrayList<>();

		this.setCubyPosition(new Point2D(0, 0));
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

	public Point2D getCubyPosition() { return cubyPosition; }

	public void setCubyPosition(Point2D cubyPosition) { this.cubyPosition = cubyPosition; }

}
