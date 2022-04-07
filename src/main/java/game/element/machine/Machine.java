package game.element.machine;

import java.util.List;
import java.util.Random;

import controler.DataCtrl;
import event.ThrowEvent;
import game.element.factory.BallFactory.TypeBalle;
import game.element.zone.Zone;
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
import util.StatObject;

public class Machine extends StackPane {

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

	private boolean isMoving = false;

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

		new Timeline(new KeyFrame(Duration.seconds(1), e -> this.isThrowBall = true)).play();

		initialisation();
		rotationAnimation();
		throwBall();
		lifePoint();
	}

	// ------------------------------------

	/** Initialisation de la machine */
	private void initialisation() {

		// Position de la machine
		this.setLayoutX(x - (taille));
		this.setLayoutY(y - (taille));

		// ---

		// Effet de néon
		DropShadow glow = new DropShadow();
		glow.setWidth(50);
		glow.setHeight(50);
		glow.setColor(couleur);

		// Anneau de la machine
		this.anneauGen = new Circle(this.taille);
		this.anneauGen.getStrokeDashArray().addAll(20.0, 10.0);
		this.anneauGen.setStrokeWidth(5);
		this.anneauGen.setStroke(couleur);
		this.anneauGen.setFill(Color.TRANSPARENT);
		this.anneauGen.setEffect(glow);

		// Centre de la machine
		this.centreGen = new Circle(this.taille - 10);
		this.centreGen.setStroke(couleur);
		this.centreGen.setStrokeWidth(1);
		this.centreGen.setFill(Color.TRANSPARENT);

		// Point de vie de la machine
		this.timeLbl = new Label();
		this.timeLbl.setTextFill(couleur);
		this.timeLbl.setFont(Font.font("Agency FB", FontWeight.BOLD, 24));
		time();

		StackPane.setAlignment(anneauGen, Pos.CENTER);
		StackPane.setAlignment(centreGen, Pos.CENTER);
		StackPane.setAlignment(timeLbl, Pos.CENTER);

		this.getChildren().addAll(anneauGen, centreGen, timeLbl);

	}

	//--- Boucle
	
	/** Initialisation de la rotation de l'anneau exterieur de la machine */
	private void rotationAnimation() {

		this.animRotation = new RotateTransition();

		animRotation.setAxis(Rotate.Z_AXIS);
		animRotation.setByAngle(360);
		animRotation.setCycleCount(Animation.INDEFINITE);
		animRotation.setDuration(Duration.seconds(3));
		animRotation.setInterpolator(Interpolator.LINEAR);

		animRotation.setNode(anneauGen);
		animRotation.play();
	}

	/** Initialisation du chronomètre permettant de lancer les balles de la machine */
	private void throwBall() {

		timelineBall = new Timeline(new KeyFrame(Duration.millis(this.delayThrow * 1000.0), ev -> {
			TypeBalle type = this.typeBalle.get(new Random().nextInt(typeBalle.size()));
			DataCtrl.GAME_EVENT.handle(new ThrowEvent(type, this.x, this.y));
		}));

		timelineBall.setCycleCount(Animation.INDEFINITE);
		timelineBall.play();
	}

	/**Initialisation du chronomètre des points de vie de la machine*/
	private void lifePoint() {

		// Chrono de la machine

		timelineChrono = new Timeline(new KeyFrame(Duration.millis(speedLifePointChrono * 1000.0), ev -> {
			lifePoint.setCurrent(lifePoint.getCurrent() - 1);
			time();

			if (lifePoint.getCurrent() <= 0)
				destroy();

		}));

		timelineChrono.setCycleCount(Animation.INDEFINITE);
	}

	// ----

	/**
	 * Détruit la machine. Stop l'animation de rotation, le chrono et le lancer de
	 * balle
	 */
	public void destroy() {

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

	/**Mets à jour la vie de la machine*/
	private void time() {
		this.timeLbl.setText(this.lifePoint.getCurrent().toString());
	}

	public void setActived(boolean b) {

		if (!isDestroy.get()) {

			if (b)
				timelineChrono.play();
			else {
				timelineChrono.stop();
				this.lifePoint.reset();
				time();
			}
		}
	}

	/**
	 * Lie la zone à la machine. Si la machine est détruite la zone le sera aussi
	 * 
	 * @param z Zone à lier à la machine pour la destruction
	 */
	public void bindToDestroyMachine(Zone z) {

		z.getIsDisable().bind(isDestroy);
	}

	public void bindEnteredZone(BooleanBinding n) {

		this.isActived.bind(n);
		this.isActived.addListener((observable, oldValue, newValue) -> setActived(newValue));
	}

	/**
	 * Retourne le type de balle a lancer. isthrow = false
	 * 
	 * @return
	 */
	public TypeBalle lance() {

		this.isThrowBall = false;
		return this.typeBalle.get(new Random().nextInt(typeBalle.size()));
	}


	// ------------------------------------
	// Accesseur et Mutateur
	// ------------------------------------


	public double getTaille() {
		return taille;
	}

	public Color getCouleur() {
		return couleur;
	}

	public boolean isThrowBall() {
		return isThrowBall;
	}

	public BooleanProperty getIsDestroy() {
		return isDestroy;
	}

	public BooleanProperty getIsActived() {
		return isActived;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public double getCenterX() {
		return isMoving ? getTranslateX() + taille : x;
	}

	public double getCenterY() {
		return isMoving ? getTranslateY() + taille : y;
	}

	// public boolean isMoving() { return isMoving; }

	public void setMoving(boolean isMoving) {
		this.isMoving = isMoving;
	}
}
