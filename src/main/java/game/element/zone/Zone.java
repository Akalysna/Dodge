package game.element.zone;

import java.util.ArrayList;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polyline;
import javafx.util.Duration;
import util.Forme;

public class Zone extends Polyline {

	private Color couleur;

	private BooleanProperty entered;
	private BooleanProperty disable;

	private double x;
	private double y;
	
	private ArrayList<Double> points;
	private double taille; 

	public Zone(ArrayList<Double> points, double taille, int x, int y, Color color) {

		this.taille = taille; 
		this.points = points; 
		this.x = x;
		this.y = y;
		this.couleur = color;

		this.entered = new SimpleBooleanProperty(false);
		this.disable = new SimpleBooleanProperty(false);

		for (Double point : points) {
			this.getPoints().add(point * taille);
		}
		
		this.disable.addListener((observable, oldValue, newValue) -> {
			if(newValue.booleanValue())
				disable(); 
		});

		init();
	}

	public void init() {

		this.setLayoutX(x);
		this.setLayoutY(y);

		this.getStrokeDashArray().addAll(20.0, 10.0);
		this.setStroke(couleur);
		this.setStrokeWidth(3);

		DropShadow ombre = new DropShadow();
		ombre.setWidth(50);
		ombre.setHeight(50);
		ombre.setColor(couleur);

		this.setEffect(ombre);

	}


	public void hover(boolean b) {

		if (!disable.get()) {
			if (b) {
				this.setFill(couleur.deriveColor(couleur.getRed(), couleur.getGreen(), couleur.getBlue(), 0.3));
				this.entered.set(true);
			}
			else {
				this.setFill(Color.TRANSPARENT);
				this.entered.set(false);
			}
		}
	}

	public void disable() {

		Timeline fadeColor = new Timeline(
				new KeyFrame(Duration.millis(500), new KeyValue(Zone.this.strokeProperty(), Color.GRAY),
						new KeyValue(((DropShadow) Zone.this.getEffect()).colorProperty(), Color.GRAY),
						new KeyValue(Zone.this.fillProperty(), Color.TRANSPARENT)));
		fadeColor.play();
	}

	public Color getCouleur() { return couleur; }

	public BooleanProperty getEntered() { return entered; }

	public BooleanProperty getDisable() { return disable; }
}
