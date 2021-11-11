package app;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

import ctrl.CD;
import ctrl.CtrlView;
import ctrl.CtrlView.ScreenName;
import ctrl.GestionnaireNiveau;
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
import node.Cuby;
import util.EmptyLevelException;
import util.KeyTouch;
import view.GameView;
import view.HomeView;
import view.Initialisable;
import view.MapView;

public class DodgeCtrl {

	public static final int SCENE_WIDTH = 800;
	public static final int SCENE_HEIGHT = 600;

	private static Scene scene = new Scene(new BorderPane(), SCENE_WIDTH, SCENE_HEIGHT);
	public static final CtrlView ctrlView = new CtrlView(scene);;

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

		ctrlView.saveAndGoto(ScreenName.HOME, new HomeView(this));
	}

	private void action() {

		scene.setOnKeyPressed(event -> {
			this.cubyPlayer.forEach(e -> e.move(event, true));

			if (event.getCode().equals(KeyCode.H)) {
				ctrlView.goTo(ScreenName.MAP);
			}
		});

		scene.setOnKeyReleased(event -> this.cubyPlayer.forEach(e -> e.move(event, false)));
	}



	public void startGame() {
		ctrlView.saveAndGoto(ScreenName.GAME, new GameView(this));
	}

	public void gameModes(ScreenName sn) {

		switch (sn) {
		case MULTI:
			createPlayer(true);
			break;

		case MAP:

			createPlayer(false);
			ctrlView.saveScreens(ScreenName.MAP, new MapView(this));
			ctrlView.goTo(ScreenName.MAP);

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
		ctrlView.goTo(sn);
	}

	public static void loadAndGoto(ScreenName sn) {
		ctrlView.loadAndGoto(sn);
	}

	public static void saveAndGoto(ScreenName sn, Initialisable i) {
		ctrlView.saveAndGoto(sn, i);
	}

	// ---------

	private void curseur() {

		InputStream input = getClass().getResourceAsStream(CD.PATH_IMG_GAME + "cursor.png");
		Image img = new Image(input);

		scene.setCursor(new ImageCursor(img));
	}

	private void hideCursor(boolean b) {
		InputStream input = getClass().getResourceAsStream(CD.PATH_IMG_GAME + "cursor.png");
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
