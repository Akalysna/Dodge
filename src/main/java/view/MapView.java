package view;

import java.io.InputStream;

import app.DodgeCtrl;
import i18n.I18N;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class MapView extends BorderPane implements Initialisable{
	
	private DodgeCtrl dodgeCtrl; 
	
	private Button btnRetour; 
	
	private Button btnPlay;
	private Button btnNextLevel; 
	private Button btnLastLevel; 
	private ImageView cubyImage; 
	private Label titre; 
	private Slider progressBar; 
	
	
	public MapView(DodgeCtrl dodgeCtrl) {
		
		this.dodgeCtrl = dodgeCtrl; 
		init();
		action();
	
	}

	@Override
	public void init() {
		
		this.btnPlay = new Button(); 
		this.btnNextLevel = new Button(); 
		this.btnLastLevel = new Button(); 
		this.btnRetour = new Button(); 
		
		this.titre = new Label(); 
		
		this.btnPlay.textProperty().bind(I18N.createStringBinding("btn.map.play"));
	
		InputStream input = getClass().getResourceAsStream(dodgeCtrl.getCurrentLevel().getCubyPath());
		
		Image img = new Image(input); 
		this.cubyImage = new ImageView(img);
		
		initCurrentLevel();
		
		
		HBox hbox = new HBox(); 
		hbox.getChildren().addAll(cubyImage, titre ); 
		
		this.setLeft(btnLastLevel);
		this.setRight(btnNextLevel);
		this.setTop(btnRetour);
		this.setCenter(hbox);
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
