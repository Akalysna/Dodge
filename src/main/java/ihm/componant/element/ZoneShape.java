/**
 * 
 */
package ihm.componant.element;

import controler.DataCtrl;
import game.element.zone.Zone;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polyline;
import javafx.util.Duration;

/**
 * @author Llona André--Augustine
 *
 */
public class ZoneShape extends Polyline {

	private Zone zone;
	private Color color;

	public ZoneShape(Zone zone) {
		this.zone = zone;
		this.color = zone.getColor().getColor();
		
		for (Double point : zone.getPoints()) {
			this.getPoints().add(point * zone.getSize());
		}
		
		initialization();
		
		DataCtrl.THROW_EVENT.register(event -> {
			if(event.isDestroy()) {
				destroy();
			}
		});
	}

	public void initialization() {

		this.setLayoutX(zone.getPosition().getX());
		this.setLayoutY(zone.getPosition().getY());

		this.getStrokeDashArray().addAll(20.0, 10.0);
		this.setStroke(color);
		this.setStrokeWidth(3);

		DropShadow ombre = new DropShadow();
		ombre.setWidth(50);
		ombre.setHeight(50);
		ombre.setColor(color);

		this.setEffect(ombre);

	}
	
	public void addItemInZone(int i) {
		zone.addItemInZone(i);
	}
	
	public int getNbItemInZone() { return zone.getNbItemInZone(); }

	public void active() {
		zone.active();
		if (!zone.isDisable())
			this.setFill(color.deriveColor(color.getRed(), color.getGreen(), color.getBlue(), 0.3));
	}

	public void stop() {
		zone.stop();
		if (!zone.isDisable())
			this.setFill(Color.TRANSPARENT);
	}

	public void destroy() {

		Timeline fadeColor = new Timeline(new KeyFrame(Duration.millis(500), new KeyValue(strokeProperty(), Color.GRAY),
				new KeyValue(((DropShadow) getEffect()).colorProperty(), Color.GRAY),
				new KeyValue(fillProperty(), Color.TRANSPARENT)));
		fadeColor.play();
	}
	
	/**
	 * @return 
	 * 
	 */
	public boolean isHovered() {
		return zone.isHovered(); 
	}
}
