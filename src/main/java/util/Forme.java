package util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import controler.DataCtrl;

public class Forme {

	public enum SizeShape {

		SMALLEST(3), SMALL(6), NORMAL(8), BIG(12), BIGEST(14);

		private int taille;

		private SizeShape(int taille) {
			this.taille = taille;
		}

		public int getTaille() { return taille; }
	}


	private HashMap<String, ArrayList<Double>> formes;

	public Forme() {

		this.formes = new HashMap<>();
		read();
	}

	private void read() {
		try {

			String l;
			BufferedReader in = new BufferedReader(
					new InputStreamReader(getClass().getResourceAsStream(DataCtrl.PATH_NIVEAU + "forme_zone.txt")));

			while ((l = in.readLine()) != null) {

				String[] name = l.split(":");

				String[] position = name[1].split(",");

				ArrayList<Double> points = new ArrayList<>();
				for (int i = 0; i < position.length; i++) {
					points.add(Double.valueOf(position[i]));
				}

				formes.put(name[0], points);

			}

			in.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public ArrayList<Double> getPoints(String key) {
		if (formes.containsKey(key))
			return formes.get(key);

		return null;
	}
	
	public ArrayList<Double> getPoints(String key, SizeShape size) {
		
		ArrayList<Double> points = new ArrayList<>(); 
		
		if (formes.containsKey(key)) {
			
			for (Double d : formes.get(key)) {
				points.add(d*size.getTaille());
			}
			
			return points;
		}

		return points;
	}


//	private TypeForme forme;
//	private ArrayList<Double> points;
//
//	public Forme(TypeForme forme, ArrayList<Double> points) {
//		
//		this.formes = new HashMap<>(); 
//		this.forme = forme;
//		this.points = points;
//	}
//
//	public TypeForme getForme() { return forme; }
//
//	public ArrayList<Double> getPoints() { return points; }


}
