package view;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

import app.DodgeCtrl;
import ctrl.CD;
import ctrl.CtrlView.ScreenName;
import i18n.I18N;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;


/**
 * HomeView represente la fenêtre d'accueil du jeu 
 * @author Llona André--Augustine
 *
 */
public class HomeView extends BorderPane implements Initialisable {

	private Button btnOnePlayer;
	private Button btnTwoPlayer;
	private Button btnOption;
	private Button btnQuit;

	private Label title;

	private DodgeCtrl dodgeCtrl;

	/**
	 * Constructeur de la page de garde
	 * @param dodgeCtrl
	 */
	public HomeView(DodgeCtrl dodgeCtrl) {
		
		this.dodgeCtrl = dodgeCtrl;
		
		initNode();
		design();
		action();
		
		
	}
	
	@Override
	public void load() {
		
		FadeTransition fd = new FadeTransition(Duration.millis(700), this);
		fd.setFromValue(0);
		fd.setToValue(1);
		fd.playFromStart();
		
	}

	
	
	@Override
	public void initNode() {

		//Padding de la fenêtre
		this.setPadding(new Insets(20));
		
		this.btnOnePlayer = new Button();
		this.btnTwoPlayer = new Button();
		this.btnOption = new Button();
		this.btnQuit = new Button();

		this.title = new Label();

		this.btnOnePlayer.textProperty().bind(I18N.createStringBinding("btn.oneplayer"));
		this.btnTwoPlayer.textProperty().bind(I18N.createStringBinding("btn.twoplayer"));
		this.btnQuit.textProperty().bind(I18N.createStringBinding("btn.quitter"));
		this.title.textProperty().bind(I18N.createStringBinding("titre.dodge"));

		VBox centerBtn = new VBox(20, btnOnePlayer, btnTwoPlayer, btnQuit);
		centerBtn.setAlignment(Pos.CENTER);
		centerBtn.setSpacing(20);

		this.setCenter(centerBtn);
		this.setTop(title);
		this.setBottom(new HBox(btnOption));
	}

	/**
	 * Modifie l'aspect des éléments
	 */
	private void design() {

		// Background de la fenêtre
		InputStream input = getClass().getResourceAsStream(CD.PATH_IMG_GAME + "menu_fond.png");
		Image img = new Image(input);
		this.setBackground(
				new Background(new BackgroundImage(img, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
						BackgroundPosition.CENTER, new BackgroundSize(100, 100, true, true, false, true))));

		// Titre

		this.title.setFont(Font.font("Berlin Sans FB Demi", 80));
		this.title.setTextFill(Color.WHITE);
		this.title.setPadding(new Insets(80.0, 0.0, 30.0, 0.0));

		BorderPane.setAlignment(title, Pos.CENTER);

		InputStream in = getClass().getResourceAsStream(CD.PATH_IMG_GAME + "settings_gear.png");
		Image i = new Image(in);
		btnOption.setBackground(
				new Background(new BackgroundImage(i, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
						BackgroundPosition.CENTER, new BackgroundSize(40, 40, true, true, true, false))));

		btnOption.setPrefWidth(60);
		btnOption.setPrefHeight(60);
		

		btnUi(btnOnePlayer);
		btnUi(btnTwoPlayer);
		btnUi(btnQuit);

		// Crée les lignes animé en fond
		createLigne(10, 150, Color.SKYBLUE, 50, 600, 2);
		createLigne(15, 150, Color.DEEPPINK, 200, 600, 4);
		createLigne(5, 150, Color.YELLOW, 600, 600, 1);
		createLigne(10, 150, Color.YELLOW, -100, 600, 3);

	}

	/**
	 * Action sur les éléments du jeu
	 */
	private void action() {

		this.btnQuit.setOnMouseClicked(event -> {

			FadeTransition fd = new FadeTransition(Duration.millis(500), this);
			fd.setFromValue(1);
			fd.setToValue(0);

			fd.setOnFinished(e -> Platform.exit());

			fd.play();
		});

		this.btnOnePlayer.setOnMouseClicked(event -> {

			FadeTransition fd = new FadeTransition(Duration.seconds(1), this);
			fd.setFromValue(1);
			fd.setToValue(0);

			fd.setOnFinished(e -> dodgeCtrl.gameModes(ScreenName.MAP));

			fd.play();
		});

		this.btnTwoPlayer.setOnMouseClicked(event -> {

			FadeTransition fd = new FadeTransition(Duration.seconds(1), this);
			fd.setFromValue(1);
			fd.setToValue(0);

			fd.setOnFinished(e -> dodgeCtrl.gameModes(ScreenName.MULTI));

			fd.play();
		});


		// Hover des boutons

		ArrayList<Button> btn = new ArrayList<>(Arrays.asList(btnOnePlayer, btnTwoPlayer, btnQuit));

		for (Button b : btn) {

			b.setOnMouseEntered(event -> {
				backgroundImgBtn(CD.PATH_IMG_GAME + "hover_menu_btn.png", b);
			});

			b.setOnMouseExited(event -> {
				backgroundImgBtn(CD.PATH_IMG_GAME + "transparent_menu_btn.png", b);
			});
		}
	}

	
	
	/**
	 * Créer une ligne pour l'animation de fond
	 * 
	 * @param longueur Longueur de la ligne
	 * @param largeur  Largeur de la ligne
	 * @param color    Couleur de la ligne
	 * @param x        Position en X
	 * @param y        Position en Y
	 * @param vitesse  Vitesse de déplacement
	 */
	private void createLigne(int longueur, int largeur, Color color, int x, int y, int vitesse) {

		Rectangle rectangle = new Rectangle(longueur, largeur);
		rectangle.setFill(color);
		rectangle.setRotate(35);

		this.getChildren().add(0, rectangle);

		Path path = new Path(new MoveTo(x, y), new LineTo(x, y), new LineTo(x + (50 * 10), y - (71.5 * 10)));
		PathTransition pathAnimation = new PathTransition(Duration.seconds(vitesse), path, rectangle);
		pathAnimation.setCycleCount(PathTransition.INDEFINITE);
		pathAnimation.setInterpolator(Interpolator.LINEAR);
		pathAnimation.play();
	}

	/**
	 * Modifie l'apparence du bouton et du texte
	 * 
	 * @param btn Bouton a modifier
	 */
	private void btnUi(Button btn) {

		backgroundImgBtn(CD.PATH_IMG_GAME + "transparent_menu_btn.png", btn);

		btn.setPrefWidth(230);
		btn.setPrefHeight(80);

		btn.setTextFill(Color.WHITE);
		btn.setFont(Font.font("Agency FB", FontWeight.BOLD, 24));
		btn.setGraphicTextGap(10);
		btn.setTextAlignment(TextAlignment.CENTER);
	}

	/**
	 * Ajoute une image de fond au bouton
	 * 
	 * @param imgFile Chemin vers l'image
	 * @param btn     Button a modifier
	 */
	public void backgroundImgBtn(String imgFile, Button btn) {

		InputStream input = getClass().getResourceAsStream(imgFile);
		Image img = new Image(input);
		btn.setBackground(new Background(new BackgroundImage(img, BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
				new BackgroundSize(btn.getPrefWidth(), btn.getPrefHeight(), true, true, true, false))));

	}





}
