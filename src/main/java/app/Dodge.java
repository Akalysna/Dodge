package app;

import javafx.application.Application;
import javafx.stage.Stage;

public class Dodge extends Application{

	@Override
	public void start(Stage stage) throws Exception {
		
		GameCtrl gameCtrl = new GameCtrl(stage); 
		gameCtrl.run();
		
	}
	
	public static void main(String[] args) {
		Dodge.launch(args);
	}

}
