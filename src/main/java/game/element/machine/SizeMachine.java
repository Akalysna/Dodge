package game.element.machine;

import java.util.List;

import game.element.factory.BallFactory.TypeBalle;
import javafx.scene.paint.Color;

public class SizeMachine extends Machine {

	public SizeMachine(int x, int y, int taille, int lifePoint, Color color, int delayThrow, int speedLifePointChrono, List<TypeBalle> typeBalle) {
		super(x, y, taille, lifePoint, color, delayThrow, speedLifePointChrono,typeBalle);
		// TODO Auto-generated constructor stub
	}
	
	public int getBallSize() {
		
		//TODO retourner aleatoirement la taille d'une balle
		return 0; 
	}

}
