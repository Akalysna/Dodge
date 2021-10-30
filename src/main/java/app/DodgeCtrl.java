package app;

import java.util.List;

import game.niveau.Niveau;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import view.CtrlView;
import view.CtrlView.ScreenName;
import view.HomeView;
import view.MapView;

public class DodgeCtrl {

	private Stage stage;
	private Scene scene;

	private CtrlView ctrlView;
	
	public static int sceneWidth = 800; 
	public static int sceneHeight = 600; 

	public DodgeCtrl(Stage stage) {

		BorderPane root = new BorderPane(); 
		this.scene = new Scene(root, sceneWidth,sceneHeight);
		this.ctrlView = new CtrlView(scene);
		this.stage = stage;
		
		addAllScreen();
	}
	
	private void addAllScreen() {
	
		this.ctrlView.saveScreens(ScreenName.HOME, new HomeView(ctrlView));
		this.ctrlView.saveScreens(ScreenName.MAP, new MapView(ctrlView));

	}

	public void run() {
		
		Niveau niveau = new Niveau("niveau/niveau1.txt"); 
		List<game.niveau.Stage> stages = niveau.getStages(); 

		BorderPane bp = new BorderPane(); 
		bp.getChildren().addAll(stages.get(0).getNodes()); 
		
		this.scene.setRoot(bp);
		
		this.stage.setScene(scene);
		this.stage.show();

		//this.ctrlView.goTo(ScreenName.HOME);
	}
	
	



}
