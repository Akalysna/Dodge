package app;

import java.io.InputStream;

import control.view.View;
import control.view.ViewCtrl;
import controler.DataCtrl;
import controler.DodgeCtrl;
import controler.DataCtrl.ScreenName;
import ihm.GameView;
import ihm.HomeView;
import javafx.application.Application;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Dodge extends Application {

	public static final int SCENE_WIDTH = 800;
	public static final int SCENE_HEIGHT = 600;

	private double xOffset = 0;
	private double yOffset = 0;

	private Stage stage;
	private Scene scene;
	
	public static ViewCtrl<ScreenName, View> viewCtrl;

	@Override
	public void start(Stage stage) throws Exception {

		this.stage = stage;
		this.scene = new Scene(new BorderPane(), SCENE_WIDTH, SCENE_HEIGHT);

		DodgeCtrl dodgeCtrl = new DodgeCtrl(scene);

		viewCtrl = new ViewCtrl<>(scene);
		//viewCtrl.registry(ScreenName.HOME, new HomeView());
		//viewCtrl.registry(ScreenName.MAP, new MapView(dodgeCtrl));
		viewCtrl.registry(ScreenName.GAME, new GameView(dodgeCtrl));
		
		((GameView) getScreen(ScreenName.GAME)).initWorld(dodgeCtrl.getLevel(0), dodgeCtrl.getPlayers());

		dragWindow();
		setCursor("cursor.png");
		
		this.stage.setResizable(false);
		this.stage.setTitle("Dodge");
		this.stage.setScene(scene);
		this.stage.initStyle(StageStyle.UNDECORATED);

		this.stage.show();
		
		goTo(ScreenName.GAME);

	}

	public static void main(String[] args) {
		Dodge.launch(args);
	}

	//--------------
	
	/**
	 * 
	 * @param name
	 */
	public static void goTo(ScreenName name) {
		viewCtrl.goTo(name);
	}
	
	/**
	 * 
	 * @param name
	 * @return
	 */
	public static View getScreen(ScreenName name) {
		return viewCtrl.getScreen(name); 
	}
	
	//----
	
	/**
	 * Permet de déplacer la fenetre du jeu quand le style du stage est UNDECORATED
	 * 
	 * @see StageStyle#UNDECORATED
	 */
	public void dragWindow() {

		scene.setOnMousePressed(e -> {

			xOffset = e.getSceneX();
			yOffset = e.getSceneY();

		});

		scene.setOnMouseDragged(e -> {
			stage.setX(e.getScreenX() - xOffset);
			stage.setY(e.getScreenY() - yOffset);
		});
	}

	/**
	 * Change l'image du curseur
	 * @param imgName
	 */
	public void setCursor(String imgName) {

		InputStream input = getClass().getResourceAsStream(DataCtrl.PATH_IMG_GAME + imgName);
		Image img = new Image(input);

		scene.setCursor(new ImageCursor(img));
	}
}




class Launch {

	public static void main(String[] args) {
		Dodge.main(args);
	}
}