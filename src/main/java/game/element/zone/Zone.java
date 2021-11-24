package game.element.zone;

import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polyline;
import javafx.util.Duration;

public class Zone extends Polyline {

	/** Vrai si la zone est survolé */
	private BooleanProperty isHovered;

	/** Vrai si la zone est désactivé */
	private BooleanProperty isDisable;

	/** Couleur de la zone */
	private Color couleur;

	/**
	 * Constructeur de zone
	 * 
	 * @param points Liste des points pour construire la zone
	 * @param taille Taille de la zone. Chaque point est multiplié par la taille
	 * @param x      Coordonnée de la zone en X
	 * @param y      Coordonnée de la zone en Y
	 * @param color  Couleur de la zone
	 */
	public Zone(List<Double> points, double taille, int x, int y, Color color) {

		
		
		this.couleur = color;

		this.isHovered = new SimpleBooleanProperty(false);
		this.isDisable = new SimpleBooleanProperty(false);

		for (Double point : points) {
			this.getPoints().add(point * taille);
		}

		this.isDisable.addListener((observable, oldValue, newValue) -> {
			if (newValue.booleanValue()) // Si la zone dans l'état désactivé changer automatiquement son apparence
				disable();
		});

		init();
	}

	
	private void init() {

		// Modification du countour de la zone

		this.getStrokeDashArray().addAll(20.0, 10.0);
		this.setStroke(couleur);
		this.setStrokeWidth(3);

		// Ajout de l'effet lumineux (glow) sur la zone

		DropShadow ombre = new DropShadow();
		ombre.setWidth(50);
		ombre.setHeight(50);
		ombre.setColor(couleur);

		this.setEffect(ombre);
	}


	/**
	 * Change l'état de la zone si elle n'est pas désactivé ({@link #isHovered}). La
	 * couleur de fond de la zone est modifié lorsqu'elle est survolé
	 * 
	 * @param b true : la zone est survolé
	 */
	public void hover(boolean b) {

		if (!isDisable.get()) {

			if (b)
				this.setFill(couleur.deriveColor(couleur.getRed(), couleur.getGreen(), couleur.getBlue(), 0.3));
			else
				this.setFill(Color.TRANSPARENT);

			this.isHovered.set(b);
		}
	}

	/** Change le style de la zone lorsqu'elle se désactive. Devient grisatre */
	private void disable() {

		new Timeline(new KeyFrame(Duration.millis(500), new KeyValue(Zone.this.strokeProperty(), Color.GRAY),
				new KeyValue(((DropShadow) Zone.this.getEffect()).colorProperty(), Color.GRAY),
				new KeyValue(Zone.this.fillProperty(), Color.TRANSPARENT))).play();
	}

	// ---------------------------

	public Color getCouleur() { return couleur; }

	public BooleanProperty getIsHovered() { return isHovered; }

	public BooleanProperty getIsDisable() { return isDisable; }
}
