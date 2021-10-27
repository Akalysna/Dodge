package app;

import javafx.application.Application;
import javafx.stage.Stage;

public class Dodge extends Application{

	@Override
	public void start(Stage stage) throws Exception {
		
		DodgeCtrl gameCtrl = new DodgeCtrl(stage); 
		gameCtrl.run();
		
	}
	
	public static void main(String[] args) {
		Dodge.launch(args);
	}

}
