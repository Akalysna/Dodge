package ihm;

import java.io.InputStream;

import app.Dodge;
import control.view.View;
import controler.DataCtrl;
import controler.DodgeCtrl;
import controler.DataCtrl.ScreenName;
import game.niveau.GestionnaireNiveau;
import i18n.I18N;
import ihm.componant.CirclePagination;
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
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;


/**
 * MapView represente la fenêtre de choix de niveau
 * 
 * @author Llona André--Augustine
 */
public class MapView extends BorderPane implements View {

	private Button btnRetour;
	private Button btnPlay;
	private Button btnNextLevel;
	private Button btnLastLevel;

	private Label titre;

	private ProgressBar pb;
	private ImageView cubyImage;

	private CirclePagination pagination;
	private GestionnaireNiveau gestionNiveau;

	/**
	 * Constructeur de la fenêtre de choix de niveau
	 * 
	 * @param dodgeCtrl
	 */
	public MapView(DodgeCtrl dodgeCtrl) {

		this.gestionNiveau = dodgeCtrl.getGestionNiveau();

		initialization();
		design();
		events();
	}

	@Override
	public void load() {
		this.pagination.currentPage(gestionNiveau.indexCurentLevel());
		updateLevelInfo();
	}

	public void initialization() {

		this.pagination = new CirclePagination(this.gestionNiveau.nbLevel(), 0, Color.WHITE, Color.GRAY);
		this.pagination.currentPage(this.gestionNiveau.indexCurentLevel());

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

		InputStream input = getClass().getResourceAsStream(gestionNiveau.getCurrentLevel().getCubyPath());
		Image img = new Image(input);

		this.cubyImage = new ImageView(img);
		this.cubyImage.setFitWidth(200);
		this.cubyImage.setFitHeight(200);

		this.pb.setPrefWidth(300);

		// TODO changeLevelInfo();

		HBox hboxPagination = new HBox();
		hboxPagination.getChildren().addAll(pagination.getPagination());
		hboxPagination.setAlignment(Pos.CENTER);
		hboxPagination.setSpacing(10);

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
		BorderPane.setAlignment(hboxPagination, Pos.CENTER);

		this.setPadding(new Insets(30));

		this.setLeft(btnLastLevel);
		this.setRight(btnNextLevel);
		this.setTop(btnRetour);
		this.setCenter(vbox);
		this.setBottom(hboxPagination);
	}

	protected void design() {

		// Background de la fenêtre
		InputStream input = getClass().getResourceAsStream(DataCtrl.PATH_IMG_GAME + "lobby_fond.png");
		Image img = new Image(input);
		this.setBackground(
				new Background(new BackgroundImage(img, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
						BackgroundPosition.CENTER, new BackgroundSize(100, 100, true, true, false, true))));

		this.backgroundImgBtn(DataCtrl.PATH_IMG_GAME + "fleche_retour.png", btnRetour);
		this.backgroundImgBtn(DataCtrl.PATH_IMG_GAME + "arrow_empty.png", btnLastLevel);
		this.backgroundImgBtn(DataCtrl.PATH_IMG_GAME + "arrow_full.png", btnNextLevel);
		this.backgroundImgBtn(DataCtrl.PATH_IMG_GAME + "transparent_menu_btn.png", btnPlay);

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

	protected void events() {
		
		this.btnNextLevel.setOnMouseClicked(event -> {

			//Si le niveau a été changer
			if (gestionNiveau.nextLevel()) {

				this.pagination.nextPage();

				if (gestionNiveau.isLastLevel())
					navigationBtnDesign(true, false);

				else
					navigationBtnDesign(true, true);

				updateLevelInfo();
			}

		});

		this.btnLastLevel.setOnMouseClicked(event -> {

			if (gestionNiveau.lastLevel()) {

				this.pagination.previousPage();

				if (gestionNiveau.isFirstLevel())
					navigationBtnDesign(false, true);

				else
					navigationBtnDesign(true, true);

				updateLevelInfo();
			}
		});

		this.btnPlay.setOnMouseClicked(event -> Dodge.goTo(ScreenName.GAME));
		this.btnPlay
				.setOnMouseEntered(event -> backgroundImgBtn(DataCtrl.PATH_IMG_GAME + "hover_menu_btn.png", btnPlay));
		this.btnPlay
				.setOnMouseExited(e -> backgroundImgBtn(DataCtrl.PATH_IMG_GAME + "transparent_menu_btn.png", btnPlay));


		// TODO Trouver une alternative
		//this.btnRetour.setOnMouseClicked(event -> DodgeCtrl.loadAndGoto(ScreenName.HOME));

		hoverScale(btnNextLevel, 1.1);
		hoverScale(btnLastLevel, 1.1);

	}

	/**
	 * Mets à jour les informations afficher concernant le niveau
	 */
	private void updateLevelInfo() {

		InputStream input = getClass().getResourceAsStream(gestionNiveau.getCurrentLevel().getCubyPath());
		Image img = new Image(input);

		this.cubyImage.setImage(img); // Change l'image
		this.titre.setText(gestionNiveau.getCurrentLevel().getName()); // Change le nom du niveau
		this.pb.setProgress(gestionNiveau.getCurrentLevel().getProgress()); // Change la progression du niveau

		if (gestionNiveau.isLastLevel())
			navigationBtnDesign(true, false);

		else if (gestionNiveau.isFirstLevel())
			navigationBtnDesign(false, true);

		else
			navigationBtnDesign(true, true);
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
		this.backgroundImgBtn(DataCtrl.PATH_IMG_GAME + tmp, btnLastLevel);

		tmp = isNextLevel ? full : empty;
		this.backgroundImgBtn(DataCtrl.PATH_IMG_GAME + tmp, btnNextLevel);


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
