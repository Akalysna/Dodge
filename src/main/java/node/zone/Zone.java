package node.zone;

import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polyline;
import javafx.util.Duration;

public class Zone extends Polyline {

	private Color couleur;

	private boolean entered;
	private boolean disable;

	private double x;
	private double y;

	public Zone(List<Double> forme, double taille, int x, int y, Color color) {

		this.x = x;
		this.y = y;
		this.couleur = color;

		this.entered = false;
		this.disable = false;

		for (Double point : forme) {
			this.getPoints().add(point * taille);
		}

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

		if (!disable) {
			Color c = (Color) this.getStroke();

			if (b)
				this.setFill(c.deriveColor(c.getRed(), c.getGreen(), c.getBlue(), 0.3));
			else
				this.setFill(Color.TRANSPARENT);
		}
	}

	public void disable() {

		this.disable = true;

		Timeline fadeColor = new Timeline(
				new KeyFrame(Duration.millis(500), new KeyValue(Zone.this.strokeProperty(), Color.GRAY),
						new KeyValue(((DropShadow) Zone.this.getEffect()).colorProperty(), Color.GRAY),
						new KeyValue(Zone.this.fillProperty(), Color.TRANSPARENT)));
		fadeColor.play();
	}

	public Color getCouleur() { return couleur; }

	public boolean isEntered() { return entered; }

	public boolean isDisables() { return disable; }
	
	

}
