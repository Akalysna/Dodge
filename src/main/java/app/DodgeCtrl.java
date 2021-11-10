package app;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ctrl.CD;
import game.niveau.Niveau;
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
import view.CtrlView;
import view.CtrlView.ScreenName;
import view.GameView;
import view.HomeView;
import view.MapView;

public class DodgeCtrl {

	private Stage stage;
	private Scene scene;

	private CtrlView ctrlView;

	public static final int sceneWidth = 800;
	public static final int sceneHeight = 600;

	private Niveau currentLevel;

	private ArrayList<Cuby> cubyPlayer;
	private List<Niveau> niveaux;

	private static double xOffset = 0;
	private static double yOffset = 0;

	public DodgeCtrl(Stage stage) {

		BorderPane root = new BorderPane();
		this.scene = new Scene(root, sceneWidth, sceneHeight);
		this.ctrlView = new CtrlView(scene);
		this.stage = stage;

		this.cubyPlayer = new ArrayList<>();
		this.niveaux = new ArrayList<>();

		this.currentLevel = null;
		curseur();

		scene.setOnMousePressed(e -> {

			xOffset = e.getSceneX();
			yOffset = e.getSceneY();

		});

		scene.setOnMouseDragged(e -> {
			stage.setX(e.getScreenX() - xOffset);
			stage.setY(e.getScreenY() - yOffset);
		});

	}


	public void run() throws EmptyLevelException {

		uploadLevel();

		this.stage.setResizable(false);
		this.stage.setTitle("Dodge");
		this.stage.setScene(scene);
		this.stage.initStyle(StageStyle.UNDECORATED);

		this.stage.show();

		this.ctrlView.saveScreens(ScreenName.HOME, new HomeView(this));
		this.ctrlView.goTo(ScreenName.HOME);
	}

	private void move() {

		this.scene.setOnKeyPressed(event -> {
			this.cubyPlayer.forEach(e -> e.move(event, true));

			if (event.getCode().equals(KeyCode.H)) {
				ctrlView.goTo(ScreenName.MAP);
			}
		});
		this.scene.setOnKeyReleased(event -> this.cubyPlayer.forEach(e -> e.move(event, false)));
	}

	private void uploadLevel() throws EmptyLevelException {

		String l;
		BufferedReader in;

		try {

			in = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/niveau/config_niveau.txt")));

			while ((l = in.readLine()) != null) {

				String[] line = l.split(":");

				Niveau n = new Niveau(line[0], CD.PATH_CUBY + line[1], line[2],
						CD.PATH_NIVEAU + line[3]);
				this.niveaux.add(n);
			}

			in.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// TODO Lever une exception si la liste est vide, s'il n'y a aucun niveau de lu



		if (!niveaux.isEmpty())
			currentLevel = niveaux.get(0);
		else
			throw new EmptyLevelException("Aucun niveau n'a pu être lu");
	}

	public void startGame() {
		move();
		this.ctrlView.saveScreens(ScreenName.GAME, new GameView(this));
		ctrlView.goTo(ScreenName.GAME);
	}

	public void gameModes(ScreenName sn) {

		switch (sn) {
		case MULTI:
			createPlayer(true);
			break;

		case MAP:

			createPlayer(false);

			this.ctrlView.saveScreens(ScreenName.MAP, new MapView(this));
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



	/**
	 * Récupère le niveau qui suit le niveau courant
	 * 
	 * @return Niveau suivant
	 * @see DodgeCtrl#currentLevel
	 */
	public boolean nextLevel() {

		if (currentLevel != null) {

			int index = this.niveaux.indexOf(currentLevel);
			if (index < niveaux.size() - 1) {
				currentLevel = niveaux.get(index + 1);
				return true;
			}
		}

		return false;

		// TODO Ajouter une exception ?
	}

	/**
	 * Récupère le niveau precedent le niveau courant
	 * 
	 * @return Niveau precedent
	 * @see DodgeCtrl#currentLevel
	 */
	public boolean lastLevel() {
		
		if (currentLevel != null) {

			int index = this.niveaux.indexOf(currentLevel);
			
			if (index > 0) {
				currentLevel = niveaux.get(index - 1);
				return true;
			} 
		}

		return false;

		// TODO Ajouter une exception ?

	}

	public Niveau getCurrentLevel() { return currentLevel; }


	public ArrayList<Cuby> getCubyPlayer() { return cubyPlayer; }

	public List<Niveau> getNiveaux() { return niveaux; }

	public void goTo(ScreenName sn) {
		ctrlView.goTo(sn);
	}
	
	public void goToNewScreen(ScreenName sn, Parent p) {
		ctrlView.saveScreens(sn, p);
		ctrlView.goTo(sn);
	}

	private void curseur() {

		InputStream input = getClass().getResourceAsStream(CD.PATH_IMG_GAME + "cursor.png");
		Image img = new Image(input);

		scene.setCursor(new ImageCursor(img));
	}
	
	private void hideCursor(boolean b) {
		InputStream input = getClass().getResourceAsStream(CD.PATH_IMG_GAME + "cursor.png");
		Image img = new Image(input);
		
		if(b)
			scene.setCursor(Cursor.NONE);
		else 
			scene.setCursor(new ImageCursor(img));
		
	}
	
	public void hidedCursor(Parent p) {
		p.setOnMouseMoved(event -> {

			hideCursor(false);

			Timeline timelineChrono = new Timeline(new KeyFrame(Duration.seconds(2), ev -> hideCursor(true)));

			timelineChrono.play();

		});
	}
	
	public boolean isFirstLevel() {
		return niveaux.indexOf(currentLevel) == 0;
	}
	
	public boolean isLastLevel() {
		return niveaux.indexOf(currentLevel) == niveaux.size() -1;
	}
	
	public int nbLevel() {
		return this.niveaux.size(); 
	}
	
	public int indexCurentLevel() {
		return this.niveaux.indexOf(currentLevel); 
	}
	

}
