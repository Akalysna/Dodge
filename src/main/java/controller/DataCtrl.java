package controller;

import java.util.Random;

import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;

/**
 * Classe regroupant les données utiles au jeu
 * 
 * @author Llona
 */
public class DataCtrl {
	

	/**
	 * Enulération des couleurs utilisés pour le jeu
	 * 
	 * @author Llona
	 */
	public enum GameColor {

		RED("RED"), ORANGE("DARKORANGE"), YELLOW("YELLOW"), GREEN("LAWNGREEN"), BLUE("DEEPSKYBLUE"), PINK("DEEPPINK");

		/**
		 * Nom de la couleur
		 * 
		 * @see Color
		 */
		private String colorName;

		/**
		 * Constructeur de l'énumération qui prend en paramètre le nom de la couleur à
		 * associé
		 * 
		 * @param name Nom de la couleur
		 */
		private GameColor(String name) {
			this.colorName = name;
		}

		/**
		 * Retourne le nom de la couleur
		 * 
		 * @return String
		 */
		public String getColorName() { return colorName; }

		/**
		 * Retourne la couleur associé à l'énumération
		 * 
		 * @return {@link Color}
		 */
		public Color getColor() { return Color.valueOf(colorName); }
	}


	/** Chemin d'accès aux images des niveaux */
	public static final String PATH_CUBY = "/img/cuby/";

	/** Chemin d'accès aux musique du jeu */
	public static final String PATH_MUSIC = "";

	/** Chemin d'accès aux niveau du jeu */
	public static final String PATH_NIVEAU = "/niveau/";

	/** Chemin d'accès aux image du jeu */
	public static final String PATH_IMG_GAME = "/img/game/";



	/** Background de couleur foncé */
	public static final Background BG_COLOR_DARK = new Background(
			new BackgroundFill(Color.rgb(27, 30, 35), null, null));

	/**
	 * Inverse le signe d'un réel de façon aléatoire
	 * 
	 * @param d Nombre à transformer
	 * @return int 
	 */
	public static double negNb(double d) {
		return (new Random().nextInt(100)) > 50 ? -d : d;
	}

}
