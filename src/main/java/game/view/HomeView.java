package game.view;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import app.Dodge;
import control.view.View;
import controler.DataCtrl;
import controler.DataCtrl.ScreenName;
import controler.SoundCtrl;
import i18n.I18N;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
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
 * 
 * @author Llona André--Augustine
 *
 */
public class HomeView extends BorderPane implements View {

	/** Accès au mode solo */
	private Button btnOnePlayer;

	/** Accès au mode multijoueur */
	private Button btnTwoPlayer;

	/** Accès au parametre du jeu */
	private Button btnOption;

	/** Bouton pour quitter le jeu */
	private Button btnQuit;

	/** Titre du jeu */
	private Label title;

	/** Transition du noir au blanc */
	private FadeTransition fadeTransition;

	/** Construit les lignes animé de l'écran de jeu */
	private BackgroundLineHome bgLineHome;
	
	private ArrayList<SoundCtrl> sounds; 
	private SoundCtrl home_music; 
	private SoundCtrl btn_hover_sound; 

	/**
	 * Constructeur de la page de menu
	 */
	public HomeView() {

		initialization();
		design();
		events();
	}


	@Override
	public void load() {

		FadeTransition fade = new FadeTransition(Duration.seconds(1), this);
		fade.setFromValue(0);
		fade.setToValue(1);
		fade.play();
		this.bgLineHome.playAnimation();
		
		this.sounds.forEach(e -> e.play());
	}
	
	private void initSounds() {

		this.sounds = new ArrayList<>(); 
		home_music = new SoundCtrl(DataCtrl.HOME); 
		btn_hover_sound = new SoundCtrl(DataCtrl.HOVER_BTN); 
		
		home_music.play();
		
		this.sounds.addAll(Arrays.asList(home_music, btn_hover_sound)); 

	}

	public void initialization() {
		
		initSounds();

		this.bgLineHome = new BackgroundLineHome();

		this.fadeTransition = new FadeTransition(Duration.seconds(1), this);
		this.fadeTransition.setFromValue(1);
		this.fadeTransition.setToValue(0);

		// Padding de la fenêtre
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
	protected void design() {

		// Background de la fenêtre
		InputStream input = getClass().getResourceAsStream(DataCtrl.PATH_IMG_GAME + "menu_fond.png");
		Image img = new Image(input);
		this.setBackground(
				new Background(new BackgroundImage(img, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
						BackgroundPosition.CENTER, new BackgroundSize(100, 100, true, true, false, true))));

		// Titre
		this.title.setFont(Font.font("Berlin Sans FB Demi", 80));
		this.title.setTextFill(Color.WHITE);
		this.title.setPadding(new Insets(80.0, 0.0, 30.0, 0.0));

		BorderPane.setAlignment(title, Pos.CENTER);

		InputStream in = getClass().getResourceAsStream(DataCtrl.PATH_IMG_GAME + "settings_gear.png");
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
		this.bgLineHome.createLigne(10, 150, Color.SKYBLUE, 50, 600, 2);
		this.bgLineHome.createLigne(15, 150, Color.DEEPPINK, 200, 600, 4);
		this.bgLineHome.createLigne(5, 150, Color.YELLOW, 600, 600, 1);
		this.bgLineHome.createLigne(10, 150, Color.YELLOW, -100, 600, 3);

		this.getChildren().addAll(0, this.bgLineHome.getLine());

	}

	/**
	 * Gestion des evenements de l'acceuil
	 */
	protected void events() {

		this.btnQuit.setOnMouseClicked(event -> {

			home_music.fadeStop();  
			
			fadeTransition.setOnFinished(e -> Platform.exit());
			fadeTransition.play();
		});

		this.btnOnePlayer.setOnMouseClicked(event -> {

			fadeTransition.setOnFinished(e -> {
				this.bgLineHome.stopAnimation();
				this.sounds.forEach(sound -> sound.stop());
				Dodge.goTo(ScreenName.MAP);
			});

			fadeTransition.play();
		});

		this.btnTwoPlayer.setOnMouseClicked(event -> {

			fadeTransition.setOnFinished(e -> {
				this.bgLineHome.stopAnimation();
				this.sounds.forEach(sound -> sound.stop());
				Dodge.goTo(ScreenName.MULTI);
			});

			fadeTransition.play();
		});


		// Hover des boutons
		ArrayList<Button> btn = new ArrayList<>(Arrays.asList(btnOnePlayer, btnTwoPlayer, btnQuit));

		for (Button b : btn) {

			b.setOnMouseEntered(event -> {
				backgroundImgBtn(DataCtrl.PATH_IMG_GAME + "hover_menu_btn.png", b);
				btn_hover_sound.play(); 
			});
			b.setOnMouseExited(event -> {
				backgroundImgBtn(DataCtrl.PATH_IMG_GAME + "transparent_menu_btn.png", b);
				btn_hover_sound.stop();  
			});
		}
	}



	/**
	 * Modifie l'apparence du bouton et du texte
	 * 
	 * @param btn Bouton a modifier
	 */
	private void btnUi(Button btn) {

		backgroundImgBtn(DataCtrl.PATH_IMG_GAME + "transparent_menu_btn.png", btn);

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






/**
 * Permet de construire des lignes animés qui pourront être stoppé et animé
 * 
 * @author Llona
 *
 */
class BackgroundLineHome {

	/**
	 * Liste des chemins de transitions
	 */
	private ArrayList<PathTransition> pathTransition;

	public BackgroundLineHome() {
		this.pathTransition = new ArrayList<>();
	}

	/**
	 * Créer une ligne pour l'animation de fond. Les lignes sont des
	 * {@link Rectangle} stylisé. L'animation de la ligne est lancé dès sa création
	 * 
	 * @param longueur Longueur de la ligne
	 * @param largeur  Largeur de la ligne
	 * @param color    Couleur de la ligne
	 * @param x        Position en X
	 * @param y        Position en Y
	 * @param vitesse  Vitesse de déplacement
	 */
	public void createLigne(int longueur, int largeur, Color color, int x, int y, int vitesse) {

		Rectangle rectangle = new Rectangle(longueur, largeur);
		rectangle.setFill(color);
		rectangle.setRotate(35);

		Path path = new Path(new MoveTo(x, y), new LineTo(x, y), new LineTo(x + (50 * 10.0), y - (71.5 * 10)));

		PathTransition pathAnimation = new PathTransition(Duration.seconds(vitesse), path, rectangle);
		pathAnimation.setCycleCount(Animation.INDEFINITE);
		pathAnimation.setInterpolator(Interpolator.LINEAR);
		pathAnimation.play();

		pathTransition.add(pathAnimation);
	}

	/**
	 * Stop l'animation de toutes les lignes de la classe
	 */
	public void stopAnimation() {
		pathTransition.forEach(event -> event.stop());
	}

	/**
	 * Joue l'animation de toutes les lignes de la classe
	 */
	public void playAnimation() {
		pathTransition.forEach(event -> event.play());
	}

	/**
	 * Récupère toutes les lignes de la classe
	 * 
	 * @return Liste des rectangles (ligne)
	 */
	public List<Node> getLine() {

		ArrayList<Node> shapes = new ArrayList<>();
		for (PathTransition pt : pathTransition) {
			shapes.add(pt.getNode());
		}
		
		return shapes;
	}
}
