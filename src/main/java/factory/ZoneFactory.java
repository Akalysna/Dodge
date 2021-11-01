package factory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import javafx.scene.paint.Color;
import node.zone.Zone;

public class ZoneFactory {
	
	public enum TypeZone {
		SIMPLE, MOVING, LOCKED; 
	}
	
	private static ArrayList<Double> carre = new ArrayList<Double>(
			Arrays.asList(0.0, 0.0, 20.0, 0.0, 20.0, 20.0, 0.0, 20.0, 0.0, 0.0));

	private static ArrayList<Double> petitL = new ArrayList<Double>(
			Arrays.asList(0.0, 0.0, 10.0, 0.0, 10.0, 10.0, 20.0, 10.0, 20.0, 20.0, 0.0, 20.0, 0.0, 0.0));

	private static ArrayList<Double> petitT = new ArrayList<Double>(Arrays.asList(0.0, 0.0, 30.0, 0.0, 30.0, 10.0, 20.0, 10.0,
			20.0, 20.0, 10.0, 20.0, 10.0, 10.0, 0.0, 10.0, 0.0, 0.0));

	private static ArrayList<Double> rectangleLong = new ArrayList<Double>(
			Arrays.asList(0.0, 0.0, 10.0, 0.0, 10.0, 40.0, 0.0, 40.0, 0.0, 0.0));
	
	private static ArrayList<ArrayList<Double>> shapes = new ArrayList<>(Arrays.asList(carre, petitL, petitT, rectangleLong));
	
	public ZoneFactory() {
	}
	


	public static Zone get(TypeZone tz, int x, int y, Color color) {
		switch (tz) {
		case SIMPLE:
			return simpleZone(x, y, color); 
		case MOVING:

			return null;
		case LOCKED:

		return null;

		default:
			return null; 
		}
	}

	public static Zone simpleZone(int x, int y, Color color) {
		ArrayList<Double> shape = new util.Random<ArrayList<Double>>().getRandomElement(shapes);
		return new Zone(shape, 5, x, y, color); 
	}

}
