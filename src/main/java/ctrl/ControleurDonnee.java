package ctrl;

import java.io.InputStream;
import java.util.Random;

import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
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
		
		public Color getColor() {
			return Color.valueOf(colorName); 
		}
	}
	
	public static final String PATH_CUBY = "/img/cuby/";
	public static final String PATH_MUSIC = "";
	public static final String PATH_NIVEAU = "/niveau/";
	public static final String PATH_IMG_GAME = "/img/game/";
	

	public static Color randomColor() {
		return Color.valueOf(GameColor.values()[new Random().nextInt(GameColor.values().length)].colorName);
	}
	
	public static void background(Pane scene) {
		scene.setBackground(new Background(new BackgroundFill(Color.rgb(27, 30, 35), null, null)));
	}
	
	public static Image getImage(String path) {
		
		InputStream input = ControleurDonnee.class.getClass().getResourceAsStream(path);
		return new Image(input);
	}
	
	/**
	 * Transforme un nombre en nombre négatif de façon aléatoire
	 * 
	 * @param nb Nombre à transformer
	 * @return int
	 */
	public static int negNb(int nb) {
		return (new Random().nextInt(100)) > 50 ? -nb : nb;
	}
	

}
