package game.element.factory;

import java.util.ArrayList;

import game.element.zone.Zone;
import javafx.scene.paint.Color;

public class ZoneFactory {

	public enum TypeZone {
		SIMPLE, MOVING, LOCKED;
	}

//	public enum TypeForme {
//		CARRE(20, 20), PETIT_L(20, 20), PETIT_T(30, 20), RECTANGLE_LONG(40, 10);
//
//		private double width;
//		private double height;
//
//		private TypeForme(double w, double h) {
//			this.height = h;
//			this.width = w;
//		}
//
//		public double getWidth() { return width; }
//
//		public double getHeight() { return height; }
//	}
//	
//	private final static ArrayList<Double> CARRE = new ArrayList<Double>(
//			Arrays.asList(0.0, 0.0, 20.0, 0.0, 20.0, 20.0, 0.0, 20.0, 0.0, 0.0));
//
//
//	private final static ArrayList<Double> PETIT_L = new ArrayList<Double>(
//			Arrays.asList(0.0, 0.0, 10.0, 0.0, 10.0, 10.0, 20.0, 10.0, 20.0, 20.0, 0.0, 20.0, 0.0, 0.0));
//
//	private final static ArrayList<Double> PETIT_T = new ArrayList<Double>(Arrays.asList(0.0, 0.0, 30.0, 0.0, 30.0, 10.0, 20.0,
//			10.0, 20.0, 20.0, 10.0, 20.0, 10.0, 10.0, 0.0, 10.0, 0.0, 0.0));
//
//	private final static ArrayList<Double> RECTANGLE_LONG = new ArrayList<Double>(
//			Arrays.asList(0.0, 0.0, 10.0, 0.0, 10.0, 40.0, 0.0, 40.0, 0.0, 0.0));
//	
//	
//		
//		private final static ArrayList<Forme> shapes = new ArrayList<Forme>(Arrays.asList(
//				new Forme(TypeForme.CARRE, CARRE),
//				new Forme(TypeForme.PETIT_L, PETIT_L),
//				new Forme(TypeForme.PETIT_T, PETIT_T),
//				new Forme(TypeForme.RECTANGLE_LONG, RECTANGLE_LONG)
//				)); 
//	




	public static Zone get(TypeZone tz,ArrayList<Double> points, int x, int y, Color color) {
		switch (tz) {
		case SIMPLE:
			return simpleZone(points, x, y, color);
		case MOVING:

			return null;
		case LOCKED:

			return null;

		default:
			return null;
		}
	}

	public static Zone simpleZone(ArrayList<Double> points, int x, int y, Color color) {
		return new Zone(points, 5, x, y, color);
	}

}
