package node.machine;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import util.StatObject;
import view.Initialisable;

public class Machine extends StackPane implements Initialisable {

	// ------------------------------------
	// Attribut
	// ------------------------------------

	private StatObject<Integer> lifePoint;

	private boolean isThrowBall;
	private boolean isDestroy;

	private int speedLifePointChrono;
	private int delayThrow;

	// -----

	private Color couleur;

	private int x;
	private int y;

	private double taille;


	private Circle anneauGen;
	private Circle centreGen;

	private Label timeLbl;

	private RotateTransition animRotation;

	// Chrono
	private Timeline timelineChrono;
	private Timeline timelineBall;

	// ------------------------------------
	// Constructeur
	// ------------------------------------

	public Machine(int x, int y, int taille, int lifePoint, Color color) {
		this(x, y, taille, lifePoint, color, 1000, 1000);
	}

	public Machine(int x, int y, int taille, int lifePoint, Color color, int delayThrow, int speedLifePointChrono) {

		this.couleur = color;
		this.x = x;
		this.y = y;
		this.taille = taille;

		this.lifePoint = new StatObject<>(lifePoint);

		this.delayThrow = delayThrow;
		this.speedLifePointChrono = speedLifePointChrono;

		this.isThrowBall = false;

		init();
		animMachine();
		delayThrowBall();
		lifePointChrono();
	}

	// ------------------------------------

	@Override
	public void init() {

		this.setLayoutX(x);
		this.setLayoutY(y);

		this.anneauGen = new Circle(this.taille);
		this.centreGen = new Circle(this.taille - 10 > 0 ? this.taille - 10 : 0);
		this.timeLbl = new Label();

		this.anneauGen.getStrokeDashArray().addAll(20.0, 10.0);
		this.anneauGen.setStrokeWidth(5);
		this.anneauGen.setStroke(couleur);
		this.anneauGen.setFill(Color.TRANSPARENT);

		this.centreGen.setStroke(couleur);
		this.centreGen.setStrokeWidth(1);
		this.centreGen.setFill(Color.TRANSPARENT);

		DropShadow glow = new DropShadow();
		glow.setWidth(50);
		glow.setHeight(50);
		glow.setColor(couleur);

		this.anneauGen.setEffect(glow);

		this.timeLbl.setTextFill(couleur);
		this.timeLbl.setFont(Font.font("Agency FB", FontWeight.BOLD, 24));
		this.timeLbl.textProperty().bind(Bindings.createStringBinding(() -> getChronoLP()));

		StackPane.setAlignment(anneauGen, Pos.CENTER);
		StackPane.setAlignment(centreGen, Pos.CENTER);
		StackPane.setAlignment(timeLbl, Pos.CENTER);

		this.getChildren().addAll(anneauGen, centreGen, timeLbl);

	}

	private void animMachine() {

		this.animRotation = new RotateTransition();

		animRotation.setAxis(Rotate.Z_AXIS);
		animRotation.setByAngle(360);
		animRotation.setCycleCount(Animation.INDEFINITE);
		animRotation.setDuration(Duration.seconds(3));
		animRotation.setInterpolator(Interpolator.LINEAR);

		animRotation.setNode(anneauGen);
		animRotation.play();
	}

	private void delayThrowBall() {

		timelineBall = new Timeline(
				new KeyFrame(Duration.millis(this.delayThrow * 1000.0), ev -> this.isThrowBall = true));
		timelineBall.setCycleCount(Animation.INDEFINITE);
		timelineBall.play();
	}

	// -----------

	public void destroyMachine() {

		Timeline fadeColor = new Timeline(
				new KeyFrame(Duration.millis(500), new KeyValue(Machine.this.anneauGen.strokeProperty(), Color.GRAY),
						new KeyValue(Machine.this.centreGen.strokeProperty(), Color.GRAY),
						new KeyValue(((DropShadow) Machine.this.anneauGen.getEffect()).colorProperty(), Color.GRAY),
						new KeyValue(Machine.this.timeLbl.textFillProperty(), Color.GRAY)));
		fadeColor.play();

		this.animRotation.stop();
		this.timelineBall.stop();

	}

	private void lifePointChrono() {

		// Chrono de la machine

		timelineChrono = new Timeline(new KeyFrame(Duration.seconds(speedLifePointChrono * 1000.0), ev -> {
			lifePoint.setCurrent(lifePoint.getCurrent() - 1);

			if (lifePoint.getCurrent() <= 0)
				destroyMachine();

		}));

		timelineChrono.setCycleCount(Animation.INDEFINITE);
	}

	public void setActived(boolean b) {

		if (!isDestroy) {

			if (b)
				timelineChrono.play();
			else {
				timelineChrono.stop();
				this.lifePoint.reset();
			}
		}
	}

	
	// ------------------------------------
	// Accesseur et Mutateur
	// ------------------------------------

	public boolean isDestroy() { return isDestroy; }

	public double getTaille() { return taille; }

	public Color getCouleur() { return couleur; }

	public String getChronoLP() { return lifePoint.getCurrent().toString(); }

	public boolean isThrowBall() { return isThrowBall; }

}
