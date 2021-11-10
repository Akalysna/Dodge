package view;

import java.io.InputStream;
import java.util.ArrayList;

import app.DodgeCtrl;
import ctrl.CD;
import ctrl.CtrlView.ScreenName;
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


/**
 * MapView represente la fenêtre de choix de niveau
 * @author Llona André--Augustine
 */
public class MapView extends BorderPane implements Initialisable {

	private DodgeCtrl dodgeCtrl;

	private Button btnRetour;

	private Button btnPlay;

	private Button btnNextLevel;
	private Button btnLastLevel;

	private Label titre;

	private ProgressBar pb;
	private ImageView cubyImage;

	private ArrayList<Circle> pagination;

	/**
	 * Constructeur de la fenêtre de choix de niveau
	 * @param dodgeCtrl
	 */

	public MapView(DodgeCtrl dodgeCtrl) {

		this.dodgeCtrl = dodgeCtrl;

		initNode();
		initDesign();
		initPagination();
		action();

	}

	
	@Override
	public void initNode() {

		this.pagination = new ArrayList<>();

		this.btnPlay = new Button();
		this.btnNextLevel = new Button();
		this.btnLastLevel = new Button();
		this.btnRetour = new Button();
		this.titre = new Label();
		this.pb = new ProgressBar();

		this.btnRetour.setPrefWidth(40);
		this.btnRetour.setPrefHeight(40);

		this.btnPlay.textProperty().bind(I18N.createStringBinding("btn.map.play"));
		this.btnPlay.setPrefWidth(200);
		this.btnPlay.setPrefHeight(80);

		// Image du niveau (cuby)

		InputStream input = getClass().getResourceAsStream(dodgeCtrl.getCurrentLevel().getCubyPath());
		Image img = new Image(input);

		this.cubyImage = new ImageView(img);
		this.cubyImage.setFitWidth(200);
		this.cubyImage.setFitHeight(200);

		this.pb.setPrefWidth(300);

		// TODO changeLevelInfo();

		HBox hbox = new HBox();
		hbox.getChildren().addAll(this.cubyImage, this.titre);
		hbox.setAlignment(Pos.CENTER);

		VBox vbox = new VBox();
		vbox.getChildren().addAll(hbox, this.pb, this.btnPlay);
		vbox.setAlignment(Pos.CENTER);
		vbox.setPrefWidth(200);
		vbox.setSpacing(20);


		btnNextLevel.setPrefHeight(130);
		btnNextLevel.setPrefWidth(80);

		btnLastLevel.setPrefHeight(130);
		btnLastLevel.setPrefWidth(80);


		BorderPane.setAlignment(vbox, Pos.CENTER);
		BorderPane.setAlignment(btnNextLevel, Pos.CENTER);
		BorderPane.setAlignment(btnLastLevel, Pos.CENTER);

		this.setPadding(new Insets(30));

		this.setLeft(btnLastLevel);
		this.setRight(btnNextLevel);
		this.setTop(btnRetour);
		this.setCenter(vbox);
	}

	/**
	 * Modifie l'aspect des éléments
	 */
	private void initDesign() {

		// Background de la fenêtre
		InputStream input = getClass().getResourceAsStream(CD.PATH_IMG_GAME + "lobby_fond.png");
		Image img = new Image(input);
		this.setBackground(
				new Background(new BackgroundImage(img, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
						BackgroundPosition.CENTER, new BackgroundSize(100, 100, true, true, false, true))));

		this.backgroundImgBtn(CD.PATH_IMG_GAME + "fleche_retour.png", btnRetour);
		this.backgroundImgBtn(CD.PATH_IMG_GAME + "arrow_empty.png", btnLastLevel);
		this.backgroundImgBtn(CD.PATH_IMG_GAME + "arrow_full.png", btnNextLevel);
		this.backgroundImgBtn(CD.PATH_IMG_GAME + "transparent_menu_btn.png", btnPlay);

		this.titre.setFont(Font.font("Berlin Sans FB Demi", 40));
		this.titre.setTextFill(Color.WHITE);
		this.titre.setPadding(new Insets(0.0, 30.0, 0.0, 30.0));

		this.btnPlay.setTextFill(Color.WHITE);
		this.btnPlay.setFont(Font.font("Agency FB", FontWeight.BOLD, 20));
		this.btnPlay.setGraphicTextGap(10);
		this.btnPlay.setTextAlignment(TextAlignment.CENTER);

		// Tourne le btn
		Rotate rotate = new Rotate();
		rotate.setPivotX(btnNextLevel.getPrefWidth() / 2);
		rotate.setPivotY(btnNextLevel.getPrefHeight() / 2);
		rotate.setAngle(180);
		btnNextLevel.getTransforms().add(rotate);

	}

	/**
	 * Initialise les éléments necessaire à la pagination
	 */
	private void initPagination() {

		// Création et ajout des cercles de pagination
		for (int i = 0; i < dodgeCtrl.nbLevel(); i++) {

			Circle cercle = new Circle(10);
			cercle.setFill(Color.GRAY);
			this.pagination.add(cercle);
		}

		HBox page = new HBox();
		page.getChildren().addAll(pagination);
		page.setSpacing(20);
		page.setAlignment(Pos.CENTER);

		// Change la couleur du cercle du niveau courant
		pagination.get(dodgeCtrl.indexCurentLevel()).setFill(Color.WHITE);

		BorderPane.setAlignment(page, Pos.CENTER);
		this.setBottom(page);
	}

	/**
	 * Action sur les éléments du jeu
	 */
	private void action() {

		this.btnNextLevel.setOnMouseClicked(event -> {

			if (dodgeCtrl.nextLevel()) {

				updatePageCircle();

				if (dodgeCtrl.isLastLevel())
					navigationBtnDesign(true, false);

				else
					navigationBtnDesign(true, true);

				updateLevelInfo();
			}

		});

		this.btnLastLevel.setOnMouseClicked(event -> {

			if (dodgeCtrl.lastLevel()) {

				updatePageCircle();

				if (dodgeCtrl.isFirstLevel())
					navigationBtnDesign(false, true);

				else
					navigationBtnDesign(true, true);

				updateLevelInfo();
			}
		});

		this.btnPlay.setOnMouseClicked(event -> dodgeCtrl.startGame());
		this.btnPlay.setOnMouseEntered(event -> backgroundImgBtn(CD.PATH_IMG_GAME + "hover_menu_btn.png", btnPlay));
		this.btnPlay.setOnMouseExited(e -> backgroundImgBtn(CD.PATH_IMG_GAME + "transparent_menu_btn.png", btnPlay));


		// TODO Trouver une alternative
		this.btnRetour.setOnMouseClicked(event -> dodgeCtrl.goToNewScreen(ScreenName.HOME, new HomeView(dodgeCtrl)));

		hoverScale(btnNextLevel, 1.1);
		hoverScale(btnLastLevel, 1.1);
	}

	

	/**
	 * Met à jour la pagination
	 */
	private void updatePageCircle() {

		for (Circle c : pagination) {
			c.setFill(Color.GREY);
		}

		pagination.get(dodgeCtrl.indexCurentLevel()).setFill(Color.WHITE);
	}

	/**
	 * Mets à jour les informations afficher concernant le niveau
	 */
	private void updateLevelInfo() {

		InputStream input = getClass().getResourceAsStream(dodgeCtrl.getCurrentLevel().getCubyPath());
		Image img = new Image(input);

		this.cubyImage.setImage(img); // Change l'image
		this.titre.setText(dodgeCtrl.getCurrentLevel().getName()); // Change le nom du niveau
		this.pb.setProgress(dodgeCtrl.getCurrentLevel().getProgress()); // Change la progression du niveau
	}

	/**
	 * Change l'image des boutons de navigation en fonction du niveau. S'il s'agit
	 * du dernier niveau la fleche de navigation est vide, sinon elle est pleine
	 * 
	 * @param isLastLevel Vrai s'il n'est plus possible de revenir en arrière
	 * @param isNextLevel Vrai s'il n'est plus possible d'avancer
	 */
	private void navigationBtnDesign(boolean isLastLevel, boolean isNextLevel) {

		String full = "arrow_full.png";
		String empty = "arrow_empty.png";

		String tmp = isLastLevel ? full : empty;
		this.backgroundImgBtn(CD.PATH_IMG_GAME + tmp, btnLastLevel);

		tmp = isNextLevel ? full : empty;
		this.backgroundImgBtn(CD.PATH_IMG_GAME + tmp, btnNextLevel);


		// Rotation du bouton droit

		Rotate rotate = new Rotate();
		rotate.setPivotX(btnNextLevel.getPrefWidth() / 2);
		rotate.setPivotY(btnNextLevel.getPrefHeight() / 2);
		rotate.setAngle(180);

		btnNextLevel.getTransforms().clear();
		btnNextLevel.getTransforms().add(rotate);
	}

	/**
	 * Agrandi l'élément si la souris passe dessus et revient à la taille normal
	 * 
	 * @param n     Node a modifier
	 * @param scale Agrandissement. 1 taille par défaut
	 */
	private void hoverScale(Node n, double scale) {

		n.setOnMouseEntered(event -> new Timeline(new KeyFrame(Duration.millis(200),
				new KeyValue(n.scaleXProperty(), scale), new KeyValue(n.scaleYProperty(), scale))).play());

		n.setOnMouseExited(event -> new Timeline(new KeyFrame(Duration.millis(200), new KeyValue(n.scaleXProperty(), 1),
				new KeyValue(n.scaleYProperty(), 1))).play());
	}

	// TODO factorisé cette méthode
	public void backgroundImgBtn(String imgFile, Button btn) {

		InputStream input = getClass().getResourceAsStream(imgFile);
		Image img = new Image(input);

		btn.setBackground(new Background(new BackgroundImage(img, BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
				new BackgroundSize(btn.getPrefWidth(), btn.getPrefHeight(), true, true, true, false))));
	}

}
