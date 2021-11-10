package factory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import javafx.scene.paint.Color;
import node.zone.Zone;
import util.Forme;

public class ZoneFactory {

	public enum TypeZone {
		SIMPLE, MOVING, LOCKED;
	}

	public enum TypeForme {
		CARRE(20, 20), PETIT_L(20, 20), PETIT_T(30, 20), RECTANGLE_LONG(40, 10);

		private double width;
		private double height;

		private TypeForme(double w, double h) {
			this.height = h;
			this.width = w;
		}

		public double getWidth() { return width; }

		public double getHeight() { return height; }
	}
	
	private final static ArrayList<Double> CARRE = new ArrayList<Double>(
			Arrays.asList(0.0, 0.0, 20.0, 0.0, 20.0, 20.0, 0.0, 20.0, 0.0, 0.0));


	private final static ArrayList<Double> PETIT_L = new ArrayList<Double>(
			Arrays.asList(0.0, 0.0, 10.0, 0.0, 10.0, 10.0, 20.0, 10.0, 20.0, 20.0, 0.0, 20.0, 0.0, 0.0));

	private final static ArrayList<Double> PETIT_T = new ArrayList<Double>(Arrays.asList(0.0, 0.0, 30.0, 0.0, 30.0, 10.0, 20.0,
			10.0, 20.0, 20.0, 10.0, 20.0, 10.0, 10.0, 0.0, 10.0, 0.0, 0.0));

	private final static ArrayList<Double> RECTANGLE_LONG = new ArrayList<Double>(
			Arrays.asList(0.0, 0.0, 10.0, 0.0, 10.0, 40.0, 0.0, 40.0, 0.0, 0.0));
	
	
		
		private final static ArrayList<Forme> shapes = new ArrayList<Forme>(Arrays.asList(
				new Forme(TypeForme.CARRE, CARRE),
				new Forme(TypeForme.PETIT_L, PETIT_L),
				new Forme(TypeForme.PETIT_T, PETIT_T),
				new Forme(TypeForme.RECTANGLE_LONG, RECTANGLE_LONG)
				)); 
	


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
		Forme shape = new util.Random<Forme>().getRandomElement(shapes);
		return new Zone(shapes.get(0), 5, x, y, color);
	}

}
