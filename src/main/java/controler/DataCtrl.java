package controler;

import java.util.Random;

import event.EventRegister;
import event.GameEvent;
import javafx.beans.property.DoubleProperty;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/**
 * Classe regroupant les données utiles au jeu
 * 
 * @author Llona
 */
public class DataCtrl {

	/**
	 * Couleur du jeu
	 * 
	 * @author Llona André--Augustine
	 *
	 */
	public enum DodgeColor {

		RED("RED"), ORANGE("DARKORANGE"), YELLOW("YELLOW"), GREEN("LAWNGREEN"), BLUE("DEEPSKYBLUE"), PINK("DEEPPINK"),
		WHITE("WHITE");

		/** Nom de la couleur */
		private String colorName;

		/**
		 * Constructeur de GameColor
		 * 
		 * @param name Nom de la couleur
		 */
		private DodgeColor(String name) {
			this.colorName = name;
		}

		// ------

		/**
		 * Retourne le nom de la couleur
		 * 
		 * @return Nom de couleur utilisé dans la classe {@link Color}
		 */
		public String getColorName() { return colorName; }

		/**
		 * Retourne la couleur
		 * 
		 * @return {@link Color}
		 */
		public Color getColor() { return Color.valueOf(colorName); }
	}

	public enum DodgeShape {
		ROUND, DOUBLE_ROUND, FILL, STROKE, CROSS, MULTI_ROUND;
	}

	public enum TypeElement {
		SIMPLE, GHOST, BOOM, FIRE, SIZING, INVERT;
	}

	public enum ScreenName {
		HOME, MAP, GAME, SETTING, MULTI, GAMEOVER;
	}

	// ------------------

	public static final String JSON = "/niveau/dodge.json";

	/** Chemin d'accès aux images des niveaux */
	public static final String PATH_CUBY = "/img/cuby/";

	/** Chemin d'accès aux musique du jeu */
	public static final String PATH_MUSIC = "";

	/** Chemin d'accès aux niveau du jeu */
	public static final String PATH_NIVEAU = "/niveau/";

	/** Chemin d'accès aux image du jeu */
	public static final String PATH_IMG_GAME = "/img/game/";


	public static final String HOVER_BTN = "/sounds/Hover_btn.wav";
	public static final String HOME = "/sounds/Dance.mp3";


	/** Background de couleur foncé */
	public static final Background BG_COLOR_DARK = new Background(
			new BackgroundFill(Color.rgb(27, 30, 35), null, null));

	public static final double HEIGHT = 800;
	public static final double WIDTH = 600;

	/**
	 * Construit une ligne de suivi, lorsqu'une machine lance une FocusBall
	 * 
	 * @param startX Position du début de la ligne en X
	 * @param startY Position du début de la ligne en Y
	 * @param endX   Position de la fin de la ligne en X
	 * @param endY   Position de la fin de la ligne en X
	 * @return Line
	 */
	public static Line createLine(double startX, double startY, DoubleProperty endX, DoubleProperty endY) {
		Line line = new Line();

		line.setStartX(startX);
		line.setStartY(startY);
		line.endXProperty().bind(endX);
		line.endYProperty().bind(endY);

		line.getStrokeDashArray().addAll(50.0, 30.0);
		line.setStroke(Color.rgb(40, 45, 54)); // Couleur gris
		line.setStrokeWidth(5);

		return line;
	}
}
