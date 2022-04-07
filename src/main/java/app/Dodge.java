package app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Dodge extends Application {

	@Override
	public void start(Stage stage) throws Exception {

		AnchorPane root = new AnchorPane();
		Scene scene = new Scene(root);



		stage.setScene(scene);
		stage.show();

	}

	public static void main(String[] args) {
		Dodge.launch(args);
	}
}

//------

class DodgeLaunch {

	public static void main(String[] args) {
		Dodge.main(args);
	}
}

