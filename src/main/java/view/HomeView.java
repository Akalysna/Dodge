package view;

import app.DodgeCtrl;
import i18n.I18N;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import view.CtrlView.ScreenName;

public class HomeView extends BorderPane implements Initialisable {

	private Button btnOnePlayer;
	private Button btnTwoPlayer;

	private Button btnOption;
	private Button btnQuit;

	private Label title;
	
	private DodgeCtrl dodgeCtrl; 

	public HomeView(DodgeCtrl dodgeCtrl) {
		this.dodgeCtrl = dodgeCtrl;
		init();
		action();
	}

	@Override
	public void init() {

		this.btnOnePlayer = new Button();
		this.btnTwoPlayer = new Button();
		this.btnOption = new Button();
		this.btnQuit = new Button();

		this.title = new Label();

		this.btnOnePlayer.textProperty().bind(I18N.createStringBinding("btn.oneplayer"));
		this.btnTwoPlayer.textProperty().bind(I18N.createStringBinding("btn.twoplayer"));
		this.btnOption.textProperty().bind(I18N.createStringBinding("btn.option"));
		this.btnQuit.textProperty().bind(I18N.createStringBinding("btn.quitter"));

		this.title.textProperty().bind(I18N.createStringBinding("titre.dodge"));

		VBox centerBtn = new VBox(20, btnOnePlayer, btnTwoPlayer, btnQuit);
		centerBtn.setAlignment(Pos.CENTER);
		
		
		this.setCenter(centerBtn);
		this.setTop(title);
		this.setBottom(new HBox(btnOption));

	}
	
	private void action() {
		
		this.btnQuit.setOnMouseClicked(event -> Platform.exit());
		this.btnOnePlayer.setOnMouseClicked(event -> dodgeCtrl.gameModes(ScreenName.MAP));
		this.btnTwoPlayer.setOnMouseClicked(event -> dodgeCtrl.gameModes(ScreenName.MULTI));

	}



}
