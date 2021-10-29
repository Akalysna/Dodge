package node.machine;

import javafx.scene.paint.Color;

public class SnakeMachine extends Machine {

	private int nbBallThrow;

	public SnakeMachine(int x, int y, int taille, int lifePoint, Color color, int delayThrow, int speedLifePointChrono,
			int nbBallThrow) {
		super(x, y, taille, lifePoint, color, delayThrow, speedLifePointChrono);
		this.nbBallThrow = nbBallThrow;
	}

	public SnakeMachine(int x, int y, int taille, int lifePoint, Color color, int delayThrow,
			int speedLifePointChrono) {
		super(x, y, taille, lifePoint, color, delayThrow, speedLifePointChrono);
		this.nbBallThrow = 5;
	}

	public SnakeMachine(int x, int y, int taille, int lifePoint, Color color, int nbBallThrow) {
		super(x, y, taille, lifePoint, color);
		this.nbBallThrow = nbBallThrow;
	}

	public int getNbBallThrow() { return nbBallThrow; }
}
