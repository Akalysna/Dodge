package game.element.balle;

import app.Dodge;
import controler.DataCtrl;
import controler.DataCtrl.TypeElement;
import javafx.animation.AnimationTimer;
import util.Position;

public class SimpleBall extends Ball {

	/**
	 * Constructeur de Balle
	 * 
	 * @param color   Couleur de la balle
	 * @param x       Position en x de la balle (Depuis le centre)
	 * @param y       Position en y de la balle (Depuis le centre)
	 * @param rayon   Rayon de la balle
	 * @param vitesse Vitesse de déplacement de la balle
	 */
	public SimpleBall(Position pos, int life) {
		super(pos, TypeElement.SIMPLE, life);
	}

	@Override
	protected void initBallMouvement() {

		this.mouvementTimeline = new AnimationTimer() {

			@Override
			public void handle(long now) {

				// Disparition
				if (life.getCurrent() == 0) {
					hasDisappeared = true;
				}

				position.setX(position.getX() + dx);
				position.setY(position.getY() + dy);


				// ---------------

				if (!position.inXInterval(0, Dodge.SCENE_WIDTH)) {
					dx = -dx; // Direction inversé
					changeLifePoint(-1);
				}

				if (!position.inYInterval(0, Dodge.SCENE_HEIGHT)) {
					dy = -dy; // Direction inversé
					changeLifePoint(-1);
				}
			}
		};
	}
}