package view;

import app.DodgeCtrl;
import i18n.I18N;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

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
		this.cubyImage = new ImageView(null)
		
		this.btnPlay.textProperty().bind(I18N.createStringBinding("btn.map.play"));
		
		this.getChildren().add(btnPlay); 
	}
	
	private void action() {
		this.btnPlay.setOnMouseClicked(event ->  dodgeCtrl.startGame());

	}

}
