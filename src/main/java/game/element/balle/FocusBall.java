package game.element.balle;

import app.Dodge;
import controler.DataCtrl;
import controler.DataCtrl.TypeElement;
import game.element.factory.BallFactory.TypeBalle;
import javafx.animation.AnimationTimer;
import util.Position;

public class FocusBall extends Ball {
	
	private boolean isThrow; 
	
	public FocusBall(Position pos, int life) {
		super(pos, TypeElement.FIRE, life);
		this.setThrow(false); 
	}
	
	@Override
	protected void initBallMouvement() {
		
		this.mouvementTimeline = new AnimationTimer() {

			@Override
			public void handle(long now) {

				// Disparition
				if (life.getCurrent() == 0) {
					hasDisappeared.set(true);
				}

				position.setX(position.getX() + dx);
				position.setY(position.getY() + dy);


				// ---------------

				if (!position.inXInterval(0, DataCtrl.WIDTH)) {
					dx = -dx; // Direction inversé
					changeLifePoint(-1);
				}

				if (!position.inYInterval(0, DataCtrl.HEIGHT)) {
					dy = -dy; // Direction inversé
					changeLifePoint(-1);
				}
			}
		};
		
	}

	public boolean isThrow() {
		return isThrow;
	}

	public void setThrow(boolean isThrow) {
		this.isThrow = isThrow;
		
	}



}
