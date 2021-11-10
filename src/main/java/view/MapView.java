package view;

import java.io.InputStream;

import app.DodgeCtrl;
import ctrl.CD;
import i18n.I18N;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import view.CtrlView.ScreenName;

public class MapView extends BorderPane implements Initialisable {

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

		this.setBackground(CD.BG_COLOR_DARK);
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
		
		this.btnRetour.setPrefWidth(40);
		this.btnRetour.setPrefHeight(40);
		this.backgroundImgBtn(CD.PATH_IMG_GAME + "fleche_retour.png", btnRetour);

		this.titre = new Label();
		this.titre.setFont(Font.font("Berlin Sans FB Demi", 40));
		this.titre.setTextFill(Color.WHITE);
		this.titre.setPadding(new Insets(0.0, 30.0, 0.0, 30.0));

		this.btnPlay.textProperty().bind(I18N.createStringBinding("btn.map.play"));
		this.btnPlay.setPrefWidth(200);
		this.btnPlay.setPrefHeight(80);
		
		this.btnPlay.setTextFill(Color.WHITE);
		this.btnPlay.setFont(Font.font("Agency FB", FontWeight.BOLD, 20));
		this.btnPlay.setGraphicTextGap(10);
		this.btnPlay.setTextAlignment(TextAlignment.CENTER);
		backgroundImgBtn(CD.PATH_IMG_GAME + "transparent_menu_btn.png", btnPlay);

		InputStream input = getClass().getResourceAsStream(dodgeCtrl.getCurrentLevel().getCubyPath());

		Image img = new Image(input);
		
		this.cubyImage = new ImageView(img);
		this.cubyImage.setFitWidth(200);
		this.cubyImage.setFitHeight(200);

		this.progressBar.setPrefWidth(300);
		this.progressBar.setMax(100);
		this.progressBar.setMin(0);

		this.pb = new ProgressBar();
		pb.setPrefWidth(300);

		initCurrentLevel();

		HBox hbox = new HBox();
		hbox.getChildren().addAll(cubyImage, titre);
		hbox.setAlignment(Pos.CENTER);

		VBox vbox = new VBox();
		vbox.getChildren().addAll(hbox, pb, btnPlay);
		vbox.setAlignment(Pos.CENTER);
		vbox.setPrefWidth(200);
		vbox.setSpacing(20);
		
		
		BorderPane.setAlignment(vbox, Pos.CENTER);
		

		BorderPane.setAlignment(btnNextLevel, Pos.CENTER);
		btnNextLevel.setPrefHeight(130);
		btnNextLevel.setPrefWidth(80);
		
		BorderPane.setAlignment(btnLastLevel, Pos.CENTER);
		btnLastLevel.setPrefHeight(130);
		btnLastLevel.setPrefWidth(80);

		this.setPadding(new Insets(30));
		
		this.setLeft(btnLastLevel);
		this.setRight(btnNextLevel);
		this.setTop(btnRetour);
		this.setCenter(vbox);
		//this.setBottom(btnPlay);

		design();

	}

	private void design() {

		this.backgroundImgBtn(CD.PATH_IMG_GAME + "arrow_empty.png", btnLastLevel);
		this.backgroundImgBtn(CD.PATH_IMG_GAME + "arrow_full.png", btnNextLevel);

	}

	private void initCurrentLevel() {

		InputStream input = getClass().getResourceAsStream(dodgeCtrl.getCurrentLevel().getCubyPath());

		Image img = new Image(input);
		// this.cubyImage = new ImageView(img);
		this.cubyImage.setImage(img);
		this.cubyImage.prefHeight(100);
		this.cubyImage.prefWidth(100);

		this.titre.setText(dodgeCtrl.getCurrentLevel().getName());

		this.pb.setProgress(dodgeCtrl.getCurrentLevel().calculProgress());
	}

	private void action() {

		this.btnPlay.setOnMouseClicked(event -> dodgeCtrl.startGame());

		this.btnNextLevel.setOnMouseClicked(event -> {
			dodgeCtrl.getNextLevel();
			initCurrentLevel();
		});

		this.btnLastLevel.setOnMouseClicked(event -> {
			dodgeCtrl.getLastLevel();
			initCurrentLevel();
		});
		
		btnPlay.setOnMouseEntered(event -> {
			backgroundImgBtn(CD.PATH_IMG_GAME + "hover_menu_btn.png", btnPlay);
		});

		btnPlay.setOnMouseExited(event -> {
			backgroundImgBtn(CD.PATH_IMG_GAME + "transparent_menu_btn.png", btnPlay);
		});

		this.btnRetour.setOnMouseClicked(event -> dodgeCtrl.goToNewScreen(ScreenName.HOME, new HomeView(dodgeCtrl)));
		
		hoverScale(btnNextLevel, 1.1);
		hoverScale(btnLastLevel, 1.1);

	}
	
	private void hoverScale(Node n, double scale) {
		
		n.setOnMouseEntered(event -> {

			Timeline time = new Timeline(
					new KeyFrame(Duration.millis(200), new KeyValue(n.scaleXProperty(), scale),
							new KeyValue(n.scaleYProperty(), scale)));
			time.play();

		});

		n.setOnMouseExited(event -> {

			Timeline time = new Timeline(
					new KeyFrame(Duration.millis(200), new KeyValue(n.scaleXProperty(), 1),
							new KeyValue(n.scaleYProperty(), 1)));
			time.play();

		});

	}

	public void backgroundImgBtn(String imgFile, Button btn) {

		InputStream input = getClass().getResourceAsStream(imgFile);
		Image img = new Image(input);
		btn.setBackground(new Background(new BackgroundImage(img, BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
				new BackgroundSize(btn.getPrefWidth(), btn.getPrefHeight(), true, true, true, false))));

	}

}
