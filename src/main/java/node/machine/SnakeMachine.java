package node.machine;

import java.util.Arrays;

import factory.BallFactory.TypeBalle;
import javafx.scene.paint.Color;

public class SnakeMachine extends Machine {

	private int nbBallThrow;

	public SnakeMachine(int x, int y, int taille, int lifePoint, Color color, int delayThrow, int speedLifePointChrono,
			int nbBallThrow) {
		super(x, y, taille, lifePoint, color, delayThrow, speedLifePointChrono, Arrays.asList(TypeBalle.SIMPLE));
		this.nbBallThrow = nbBallThrow;
	}

	public SnakeMachine(int x, int y, int taille, int lifePoint, Color color, int delayThrow,
			int speedLifePointChrono) {
		super(x, y, taille, lifePoint, color, delayThrow, speedLifePointChrono, Arrays.asList(TypeBalle.SIMPLE));
		this.nbBallThrow = 5;
	}

	public SnakeMachine(int x, int y, int taille, int lifePoint, Color color, int nbBallThrow) {
		super(x, y, taille, lifePoint, color, Arrays.asList(TypeBalle.SIMPLE));
		this.nbBallThrow = nbBallThrow;
	}

	public int getNbBallThrow() { return nbBallThrow; }
}
