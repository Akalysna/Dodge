package app;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import view.CtrlView;
import view.HomeView;
import view.MapView;
import view.CtrlView.ScreenName;

public class DodgeCtrl {

	private Stage stage;
	private Scene scene;

	private CtrlView ctrlView;

	public DodgeCtrl(Stage stage) {

		BorderPane root = new BorderPane(); 
		this.scene = new Scene(root, 800,600);
		this.ctrlView = new CtrlView(scene);
		this.stage = stage;
		
		addAllScreen();
	}
	
	private void addAllScreen() {
	
		this.ctrlView.saveScreens(ScreenName.HOME, new HomeView(ctrlView));
		this.ctrlView.saveScreens(ScreenName.MAP, new MapView(ctrlView));

	}

	public void run() {

		
		this.stage.setScene(scene);
		this.stage.show();

		this.ctrlView.goTo(ScreenName.HOME);
	}
	
	



}
