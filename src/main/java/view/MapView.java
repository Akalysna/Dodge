package view;

import java.io.InputStream;

import app.DodgeCtrl;
import i18n.I18N;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MapView extends BorderPane implements Initialisable{
	
	private DodgeCtrl dodgeCtrl; 
	
	private Button btnRetour; 
	
	private Button btnPlay;
	private Button btnNextLevel; 
	private Button btnLastLevel; 
	private ImageView cubyImage; 
	private Label titre; 
	private Slider progressBar;
	private ProgressBar pb;
	
	
	public MapView(DodgeCtrl dodgeCtrl) {
		
		this.dodgeCtrl = dodgeCtrl; 
		init();
		action();
	
	}

	@Override
	public void init() {
		
		this.progressBar = new Slider(); 
		this.btnPlay = new Button(); 
		this.btnNextLevel = new Button(); 
		this.btnLastLevel = new Button(); 
		this.btnRetour = new Button(); 
		
		this.titre = new Label(); 
		
		this.btnPlay.textProperty().bind(I18N.createStringBinding("btn.map.play"));
	
		InputStream input = getClass().getResourceAsStream(dodgeCtrl.getCurrentLevel().getCubyPath());
		
		Image img = new Image(input); 
		this.cubyImage = new ImageView(img);
		
		this.progressBar.setPrefWidth(300);
		this.progressBar.setMax(100);
		this.progressBar.setMin(0);
		
		this.pb = new ProgressBar(); 
		pb.setPrefWidth(300);
		
		initCurrentLevel();
		
		HBox hbox = new HBox(); 
		hbox.getChildren().addAll(cubyImage, titre ); 
		
		VBox vbox = new VBox(); 
		vbox.getChildren().addAll(hbox, pb); 
		
		this.setLeft(btnLastLevel);
		this.setRight(btnNextLevel);
		this.setTop(btnRetour);
		this.setCenter(vbox);
		this.setBottom(btnPlay);
		
	}
	
	private void initCurrentLevel() {
		
		InputStream input = getClass().getResourceAsStream(dodgeCtrl.getCurrentLevel().getCubyPath());
	
		Image img = new Image(input); 
	//	this.cubyImage = new ImageView(img); 
		this.cubyImage.setImage(img);
		this.cubyImage.prefHeight(100); 
		this.cubyImage.prefWidth(100); 
		
		this.titre.setText(dodgeCtrl.getCurrentLevel().getName());
		
		this.pb.setProgress(dodgeCtrl.getCurrentLevel().calculProgress());
	}
	
	private void action() {
		
		this.btnPlay.setOnMouseClicked(event ->  dodgeCtrl.startGame());
		
		this.btnNextLevel.setOnMouseClicked(event -> {
			dodgeCtrl.getNextLevel(); 
			initCurrentLevel();
		});
		
		this.btnLastLevel.setOnMouseClicked(event -> {
			dodgeCtrl.getLastLevel(); 
			initCurrentLevel();
		});

	}

}
