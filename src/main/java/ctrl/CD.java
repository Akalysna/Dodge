package ctrl;

import java.io.InputStream;
import java.util.Random;

import app.Dodge;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class CD {
	
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
	
	public static final Background BG_COLOR_DARK = new Background(new BackgroundFill(Color.rgb(27, 30, 35), null, null));

	public static Color randomColor() {
		return Color.valueOf(GameColor.values()[new Random().nextInt(GameColor.values().length)].colorName);
	}
	
	public static void background(Pane scene) {
		scene.setBackground(new Background(new BackgroundFill(Color.rgb(27, 30, 35), null, null)));
	}
	
	public static Image getImage(String path) {
		
		InputStream input = CD.class.getClass().getResourceAsStream(path);
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
	
	/**
	 * Ajoute une image de fond au bouton
	 * 
	 * @param imgFile
	 * @param btn
	 */
	public static void backgroundImgBtn(String imgFile, Button btn) {

		InputStream input = CD.class.getResourceAsStream("/src/main/resources"+imgFile);
		System.out.println(imgFile);
		Image img = new Image(input);
		btn.setBackground(
				new Background(new BackgroundImage(img, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
						BackgroundPosition.CENTER, new BackgroundSize(btn.getPrefWidth(), btn.getPrefHeight(), true, true, true, false))));
	}
	

}
