package util;

import java.util.ArrayList;

import factory.ZoneFactory.TypeForme;

public class Forme {

	private TypeForme forme;
	private ArrayList<Double> points;

	public Forme(TypeForme forme, ArrayList<Double> points) {
		this.forme = forme;
		this.points = points;
	}

	public TypeForme getForme() {
		return forme;
	}

	public ArrayList<Double> getPoints() {
		return points;
	}
	
	
}
