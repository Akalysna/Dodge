/**
 * 
 */
package ihm.componant.element;

import controler.DataCtrl;
import game.element.machine.Throwball;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
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

/**
 * @author Llona André--Augustine
 *
 */
public class ThrowballShape extends StackPane {

	private Throwball machine;

	private Circle anneauGen;
	private Circle centreGen;

	private Label timeLbl;

	private RotateTransition animRotation;

	private Timeline timelineChrono;
	private Timeline timelineBall;


	public ThrowballShape(Throwball machine) {
		this.machine = machine;
		initialisation();
		initAnimation();
		lifePointChrono();

		DataCtrl.THROW_EVENT.register(event -> {
			if (event.isDestroy()) {
				destroyMachine();
			}
		});


	}

	public void initialisation() {

		Color color = machine.getColor().getColor();

		this.setLayoutX(machine.getPosition().getX() - (machine.getRadius()));
		this.setLayoutY(machine.getPosition().getY() - (machine.getRadius()));

		this.anneauGen = new Circle(machine.getRadius());
		this.centreGen = new Circle(machine.getRadius() - 10);
		this.timeLbl = new Label();

		this.anneauGen.getStrokeDashArray().addAll(20.0, 10.0);
		this.anneauGen.setStrokeWidth(5);
		this.anneauGen.setStroke(color);
		this.anneauGen.setFill(Color.TRANSPARENT);

		this.centreGen.setStroke(color);
		this.centreGen.setStrokeWidth(1);
		this.centreGen.setFill(Color.TRANSPARENT);

		DropShadow glow = new DropShadow();
		glow.setWidth(50);
		glow.setHeight(50);
		glow.setColor(color);

		this.anneauGen.setEffect(glow);

		this.timeLbl.setTextFill(color);
		this.timeLbl.setFont(Font.font("Agency FB", FontWeight.BOLD, 24));
		chronoLbl();

		StackPane.setAlignment(anneauGen, Pos.CENTER);
		StackPane.setAlignment(centreGen, Pos.CENTER);
		StackPane.setAlignment(timeLbl, Pos.CENTER);

		this.getChildren().addAll(anneauGen, centreGen, timeLbl);

	}

	private void initAnimation() {

		this.animRotation = new RotateTransition();

		animRotation.setAxis(Rotate.Z_AXIS);
		animRotation.setByAngle(360);
		animRotation.setCycleCount(Animation.INDEFINITE);
		animRotation.setDuration(Duration.seconds(3));
		animRotation.setInterpolator(Interpolator.LINEAR);

		animRotation.setNode(anneauGen);
		animRotation.play();
	}

	public void destroyMachine() {

		chronoLbl();

		Timeline fadeColor = new Timeline(
				new KeyFrame(Duration.millis(500), new KeyValue(anneauGen.strokeProperty(), Color.GRAY),
						new KeyValue(centreGen.strokeProperty(), Color.GRAY),
						new KeyValue(((DropShadow) anneauGen.getEffect()).colorProperty(), Color.GRAY),
						new KeyValue(timeLbl.textFillProperty(), Color.GRAY)));
		fadeColor.play();

		this.animRotation.stop();
		this.timelineChrono.stop();
		

	}
	
	/**
	 * Retourne l'état de la machine, si elle est détruite ou non
	 * @return <code>true</code> La machine est détruite
	 */
	public boolean isDestroy() {
		return machine.isDestroy();
	}
	

	private void lifePointChrono() {

		// Chrono de la machine

		timelineChrono = new Timeline(new KeyFrame(Duration.millis(500), ev -> {
			chronoLbl();
		}));

		timelineChrono.setCycleCount(Animation.INDEFINITE);
		timelineChrono.play();
	}

	private void chronoLbl() {
		this.timeLbl.setText(String.valueOf(machine.getLife()));
	}

	/**
	 * Change l'état du lancé de balle de la machine
	 * 
	 * @param b <code>true</code> Activé le lancé de balle <code>false</code>
	 *          Désactivé le lancé de balle
	 */
	public void activeThrow(boolean b) {
		this.machine.activeThrow(b);
	}

}
