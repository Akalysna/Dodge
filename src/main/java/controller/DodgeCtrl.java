package controller;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

import controller.ViewCtrl.ScreenName;
import game.element.Cuby;
import game.niveau.GestionnaireNiveau;
import game.view.GameView;
import game.view.HomeView;
import game.view.Initialisable;
import game.view.MapView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import util.EmptyLevelException;
import util.KeyTouch;

public class DodgeCtrl {

	public static final int SCENE_WIDTH = 800;
	public static final int SCENE_HEIGHT = 600;

	private static Scene scene = new Scene(new BorderPane(), SCENE_WIDTH, SCENE_HEIGHT);
	public static final ViewCtrl viewCtrl = new ViewCtrl(scene);;

	private Stage stage;

	private ArrayList<Cuby> cubyPlayer;

	private double xOffset = 0;
	private double yOffset = 0;

	private GestionnaireNiveau gestionNiveau;

	public DodgeCtrl(Stage stage) throws EmptyLevelException {

		this.stage = stage;

		this.cubyPlayer = new ArrayList<>();

		this.gestionNiveau = new GestionnaireNiveau();

		curseur();
		dragWindow();
		action();
	}

	/**
	 * Permet de déplacer la fenetre du jeu quand le style du stage est UNDECORATED
	 * 
	 * @see StageStyle#UNDECORATED
	 */
	private void dragWindow() {

		scene.setOnMousePressed(e -> {

			xOffset = e.getSceneX();
			yOffset = e.getSceneY();

		});

		scene.setOnMouseDragged(e -> {
			stage.setX(e.getScreenX() - xOffset);
			stage.setY(e.getScreenY() - yOffset);
		});
	}

	public void run() {

		this.stage.setResizable(false);
		this.stage.setTitle("Dodge");
		this.stage.setScene(scene);
		this.stage.initStyle(StageStyle.UNDECORATED);

		this.stage.show();

		viewCtrl.saveAndGoto(ScreenName.HOME, new HomeView(this));
	}

	private void action() {

		scene.setOnKeyPressed(event -> {
			this.cubyPlayer.forEach(e -> e.move(event.getCode(), true));

			if (event.getCode().equals(KeyCode.H)) {
				viewCtrl.goTo(ScreenName.MAP);
			}
		});

		scene.setOnKeyReleased(event -> this.cubyPlayer.forEach(e -> e.move(event.getCode(), false)));
	}



	public void startGame() {
		viewCtrl.saveAndGoto(ScreenName.GAME, new GameView(this));
	}

	public void gameModes(ScreenName sn) {

		switch (sn) {
		case MULTI:
			createPlayer(true);
			break;

		case MAP:

			createPlayer(false);
			viewCtrl.saveScreens(ScreenName.MAP, new MapView(this));
			viewCtrl.goTo(ScreenName.MAP);

			break;

		default:
			break;
		}
	}

	/**
	 * Créer les cubys des joueurs ainsi que leur touche de controle
	 * 
	 * @param isMultiPalyer </br>
	 *                      true : Deux cuby sont crée</br>
	 *                      false : Un cuby est crée
	 * @see KeyTouch
	 */
	private void createPlayer(boolean isMultiPalyer) {

		KeyTouch kt = new KeyTouch(Arrays.asList(KeyCode.UP, KeyCode.RIGHT, KeyCode.DOWN, KeyCode.LEFT));
		KeyTouch kt2 = new KeyTouch(Arrays.asList(KeyCode.Z, KeyCode.D, KeyCode.S, KeyCode.Q));

		if (isMultiPalyer)
			this.cubyPlayer.addAll(Arrays.asList(new Cuby(Color.WHITE, kt2), new Cuby(Color.WHITE, kt)));
		else
			this.cubyPlayer.add(new Cuby(Color.WHITE, kt));
	}



	// ---------

	public static void goTo(ScreenName sn) {
		viewCtrl.goTo(sn);
	}

	public static void loadAndGoto(ScreenName sn) {
		viewCtrl.loadAndGoto(sn);
	}

	public static void saveAndGoto(ScreenName sn, Initialisable i) {
		viewCtrl.saveAndGoto(sn, i);
	}

	// ---------

	private void curseur() {

		InputStream input = getClass().getResourceAsStream(DataCtrl.PATH_IMG_GAME + "cursor.png");
		Image img = new Image(input);

		scene.setCursor(new ImageCursor(img));
	}

	private void hideCursor(boolean b) {
		InputStream input = getClass().getResourceAsStream(DataCtrl.PATH_IMG_GAME + "cursor.png");
		Image img = new Image(input);

		if (b)
			scene.setCursor(Cursor.NONE);
		else
			scene.setCursor(new ImageCursor(img));

	}

	public void invisibleCursor(Parent p) {
		p.setOnMouseMoved(event -> {

			hideCursor(false);

			Timeline timelineChrono = new Timeline(new KeyFrame(Duration.seconds(2), ev -> hideCursor(true)));

			timelineChrono.play();
		});
	}

	public GestionnaireNiveau getGestionNiveau() { return gestionNiveau; }

	public ArrayList<Cuby> getCubyPlayer() { return cubyPlayer; }



}
