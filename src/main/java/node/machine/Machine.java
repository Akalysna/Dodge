package node.machine;

import java.util.List;
import java.util.Random;

import factory.BallFactory.TypeBalle;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
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
import node.zone.Zone;
import util.Initialisable;
import util.StatObject;

public class Machine extends StackPane implements Initialisable {

	// ------------------------------------
	// Attribut
	// ------------------------------------

	private StatObject<Integer> lifePoint;

	private boolean isThrowBall;

	private BooleanProperty isDestroy;
	private BooleanProperty isActived;

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

	private List<TypeBalle> typeBalle;

	// ------------------------------------
	// Constructeur
	// ------------------------------------

	/**
	 * Constructeur d'une machine simple
	 * 
	 * @param x         Position en X
	 * @param y         Position en Y
	 * @param taille    Taille de la machine
	 * @param lifePoint Point de vie de la machine
	 * @param color     Couleur de la machine
	 * @param typeBalle Les types de balles que la machine lance
	 */
	public Machine(int x, int y, int taille, int lifePoint, Color color, List<TypeBalle> typeBalle) {
		this(x, y, taille, lifePoint, color, 6, 1, typeBalle);
	}

	/**
	 * 
	 * @param x                    Position en X
	 * @param y                    Position en Y
	 * @param taille               Taille de la machine
	 * @param lifePoint            Point de vie de la machine
	 * @param color                Couleur de la machine
	 * @param delayThrow           Delai entre chaque lancer
	 * @param speedLifePointChrono Vitesse du decompte des points de vie
	 */
	public Machine(int x, int y, int taille, int lifePoint, Color color, int delayThrow, int speedLifePointChrono,
			List<TypeBalle> typeBalle) {
		
		this.timeLbl = new Label(); 

		this.couleur = color;
		this.x = x;
		this.y = y;
		this.taille = taille;

		this.lifePoint = new StatObject<>(lifePoint);

		this.delayThrow = delayThrow;
		this.speedLifePointChrono = speedLifePointChrono;

		this.isThrowBall = false;
		this.isDestroy = new SimpleBooleanProperty(false);
		this.isActived = new SimpleBooleanProperty(false);
		
		this.typeBalle = typeBalle;

		init();
		animMachine();
		delayThrowBall();
		lifePointChrono();
	}

	// ------------------------------------
	
	private double getCenter() {
		return taille /2.0; 
	}

	@Override
	public void init() {

		this.setLayoutX(x-(taille));
		this.setLayoutY(y-(taille));

		this.anneauGen = new Circle(this.taille);
		this.centreGen = new Circle(this.taille - 10);
		//this.timeLbl = new Label();

		
		
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
		chronoLbl();

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

		this.isDestroy.set(true);

		Timeline fadeColor = new Timeline(
				new KeyFrame(Duration.millis(500), new KeyValue(Machine.this.anneauGen.strokeProperty(), Color.GRAY),
						new KeyValue(Machine.this.centreGen.strokeProperty(), Color.GRAY),
						new KeyValue(((DropShadow) Machine.this.anneauGen.getEffect()).colorProperty(), Color.GRAY),
						new KeyValue(Machine.this.timeLbl.textFillProperty(), Color.GRAY)));
		fadeColor.play();

		this.animRotation.stop();
		this.timelineBall.stop();
		this.timelineChrono.stop();

	}

	private void lifePointChrono() {

		// Chrono de la machine

		timelineChrono = new Timeline(new KeyFrame(Duration.millis(speedLifePointChrono * 1000.0), ev -> {
			lifePoint.setCurrent(lifePoint.getCurrent() - 1);
			chronoLbl();

			if (lifePoint.getCurrent() <= 0)
				destroyMachine();

		}));

		timelineChrono.setCycleCount(Animation.INDEFINITE);
	}

	private void chronoLbl() {
		this.timeLbl.setText(this.lifePoint.getCurrent().toString());
	}

	public void setActived(boolean b) {

		if (!isDestroy.get()) {

			if (b)
				timelineChrono.play();
			else {
				timelineChrono.stop();
				this.lifePoint.reset();
				chronoLbl();
			}
		}
	}

	/**
	 * Lie la zone à la machine. Si la machine est détruite la zone le sera aussi
	 * @param z Zone à lier à la machine pour la destruction
	 */
	public void bindToDestroyMachine(Zone z) {

		z.getDisable().bind(isDestroy);
	}

	public void bindEnteredZone(BooleanBinding n) {

		this.isActived.bind(n);
		this.isActived.addListener((observable, oldValue, newValue) -> setActived(newValue));
	}
	
	/**
	 * Retourne le type de balle a lancer. isthrow = false
	 * @return 
	 */
	public TypeBalle lance() {
		
		this.isThrowBall = false; 
		return this.typeBalle.get(new Random().nextInt(typeBalle.size()));
	}


	// ------------------------------------
	// Accesseur et Mutateur
	// ------------------------------------


	public double getTaille() { return taille; }

	public Color getCouleur() { return couleur; }

	public boolean isThrowBall() { return isThrowBall; }

	public BooleanProperty getIsDestroy() { return isDestroy; }

	public BooleanProperty getIsActived() { return isActived; }

	public int getX() { return x; }

	public int getY() { return y; }

	public double getCenterX() { return x+(taille/2); }

	public double getCenterY() { return y+(taille/2); }


}
