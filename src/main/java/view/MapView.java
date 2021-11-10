package view;

import java.io.InputStream;
import java.util.ArrayList;

import app.DodgeCtrl;
import ctrl.CD;
import game.niveau.Niveau;
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
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Rotate;
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
	
	private ArrayList<Circle> pagination; 


	public MapView(DodgeCtrl dodgeCtrl) {

		this.setBackground(CD.BG_COLOR_DARK);
		this.dodgeCtrl = dodgeCtrl;
		init();
		action();

	}

	@Override
	public void init() {

		this.pagination = new ArrayList<>(); 
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

		design();
		cerclePagination();

	}
	
	private void cerclePagination() {
		
		for (int i = 0; i < dodgeCtrl.nbLevel(); i++) {
			Circle cercle = new Circle(10); 
			cercle.setFill(Color.GRAY);
			this.pagination.add(cercle); 
			
		}
		
		HBox page = new HBox(); 
		page.getChildren().addAll(pagination); 
		page.setSpacing(20);
		page.setAlignment(Pos.CENTER);
		
		pagination.get(dodgeCtrl.indexCurentLevel()).setFill(Color.WHITE);
		
		BorderPane.setAlignment(page, Pos.CENTER);
		this.setBottom(page);
		
	}
	
	private void changePageCircle() {
		
		for (Circle c : pagination) {
			c.setFill(Color.GREY); 
		}
		
		pagination.get(dodgeCtrl.indexCurentLevel()).setFill(Color.WHITE);

	}

	private void design() {

		this.backgroundImgBtn(CD.PATH_IMG_GAME + "arrow_empty.png", btnLastLevel);
		this.backgroundImgBtn(CD.PATH_IMG_GAME + "arrow_full.png", btnNextLevel);

		Rotate rotate = new Rotate();
		rotate.setPivotX(btnNextLevel.getPrefWidth() / 2);
		rotate.setPivotY(btnNextLevel.getPrefHeight() / 2);
		rotate.setAngle(180);
		btnNextLevel.getTransforms().add(rotate);

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

	private void switchBtnLevel(boolean lastIsFull, boolean nextIsFull) {

		String full = "arrow_full.png";
		String empty = "arrow_empty.png";

		String tmp = lastIsFull ? full : empty;
		this.backgroundImgBtn(CD.PATH_IMG_GAME + tmp, btnLastLevel);

		tmp = nextIsFull ? full : empty;
		this.backgroundImgBtn(CD.PATH_IMG_GAME + tmp, btnNextLevel);

		Rotate rotate = new Rotate();
		rotate.setPivotX(btnNextLevel.getPrefWidth() / 2);
		rotate.setPivotY(btnNextLevel.getPrefHeight() / 2);
		rotate.setAngle(180);

		btnNextLevel.getTransforms().clear();
		btnNextLevel.getTransforms().add(rotate);
	}

	private void action() {

		this.btnNextLevel.setOnMouseClicked(event -> {

			if (dodgeCtrl.nextLevel()) {
				
				changePageCircle();

				if (dodgeCtrl.isLastLevel())
					switchBtnLevel(true, false);

				else
					switchBtnLevel(true, true);

				initCurrentLevel();
			}
		});

		this.btnLastLevel.setOnMouseClicked(event -> {

			if (dodgeCtrl.lastLevel()) {
				
				changePageCircle();

				if (dodgeCtrl.isFirstLevel())
					switchBtnLevel(false, true);

				else
					switchBtnLevel(true, true);

				initCurrentLevel();
			}
		});

		this.btnPlay.setOnMouseClicked(event -> dodgeCtrl.startGame());
		this.btnPlay.setOnMouseEntered(event -> backgroundImgBtn(CD.PATH_IMG_GAME + "hover_menu_btn.png", btnPlay));
		this.btnPlay
				.setOnMouseExited(event -> backgroundImgBtn(CD.PATH_IMG_GAME + "transparent_menu_btn.png", btnPlay));

		this.btnRetour.setOnMouseClicked(event -> dodgeCtrl.goToNewScreen(ScreenName.HOME, new HomeView(dodgeCtrl)));

		hoverScale(btnNextLevel, 1.1);
		hoverScale(btnLastLevel, 1.1);
	}

	/**
	 * Agrandi l'élément si la souris passe dessus et revient à la taille normal
	 * 
	 * @param n     Node a modifier
	 * @param scale Agrandissement. 1 taille par défaut
	 */
	private void hoverScale(Node n, double scale) {

		n.setOnMouseEntered(event ->

		new Timeline(new KeyFrame(Duration.millis(200), new KeyValue(n.scaleXProperty(), scale),
				new KeyValue(n.scaleYProperty(), scale))).play());

		n.setOnMouseExited(event ->

		new Timeline(new KeyFrame(Duration.millis(200), new KeyValue(n.scaleXProperty(), 1),
				new KeyValue(n.scaleYProperty(), 1))).play());
	}

	public void backgroundImgBtn(String imgFile, Button btn) {

		InputStream input = getClass().getResourceAsStream(imgFile);
		Image img = new Image(input);

		btn.setBackground(new Background(new BackgroundImage(img, BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
				new BackgroundSize(btn.getPrefWidth(), btn.getPrefHeight(), true, true, true, false))));
	}

}
