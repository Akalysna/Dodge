package util;

import javafx.geometry.Pos;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ProgressBar extends StackPane {

	private Rectangle contour; 
	private Rectangle progress; 
	
	
	public ProgressBar(double width, double height, Color color) {
		this.contour = new Rectangle(width, height); 
		this.progress = new Rectangle(width, 10);
		
		DropShadow glow = new DropShadow();
		glow.setWidth(50);
		glow.setHeight(50);
		glow.setColor(color);

		this.contour.setEffect(glow);
		
		this.contour.setFill(Color.TRANSPARENT);
		this.contour.setArcHeight(50);
		this.contour.setArcWidth(50);
		this.contour.setStroke(color);
		this.contour.setStrokeWidth(3);
		
		this.progress.setFill(color);
		
		this.setAlignment(Pos.TOP_CENTER);
		
		this.getChildren().addAll(contour, progress); 
	}
}
