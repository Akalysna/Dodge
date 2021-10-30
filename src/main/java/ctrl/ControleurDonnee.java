package ctrl;

import java.util.Random;

import javafx.scene.paint.Color;

public class ControleurDonnee {
	
	public enum GameColor {
		
		RED("RED"),
		ORANGE("DARKORANGE"),
		YELLOW("YELLOW"),
		GREEN("LAWNGREEN"),
		BLUE("DEEPSKYBLUE"),
		PINK("DEEPPINK");
		
		private String colorName; 
		
		private GameColor(String name) {
			this.colorName = name; 
		}

		public String getColorName() { return colorName; }
	}
	
	public static Color randomColor() {
		return Color.valueOf(GameColor.values()[new Random().nextInt(GameColor.values().length)].colorName);
	}

}
