package app;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import node.machine.Machine;
import view.CtrlView;
import view.HomeView;
import view.MapView;
import view.CtrlView.ScreenName;

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

//		BorderPane bp = new BorderPane(); 
//		bp.getChildren().add(new Machine(50, 50, 50, 5, Color.RED)); 
//		
//		this.scene.setRoot(bp);
		
		this.stage.setScene(scene);
		this.stage.show();

		this.ctrlView.goTo(ScreenName.HOME);
	}
	
	



}
