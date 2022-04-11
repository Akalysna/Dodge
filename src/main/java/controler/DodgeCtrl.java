package controler;

import java.util.ArrayList;

import control.Key;
import controler.DataCtrl.DodgeColor;
import controler.DataCtrl.ScreenName;
import game.element.Cuby;
import game.niveau.GestionnaireNiveau;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import util.EmptyLevelException;

public class DodgeCtrl {

	private ArrayList<Cuby> players;

	private GestionnaireNiveau gestionNiveau;
	private DodgeKeyboard dodgeKeyboard;
	public static final FormeManager formeManager = new FormeManager();


	public DodgeCtrl(Scene scene) throws EmptyLevelException {

		this.players = new ArrayList<>();

		this.gestionNiveau = new GestionnaireNiveau();

		this.dodgeKeyboard = new DodgeKeyboard(scene);

		initPlayer();
		initKeyBoard();
	}

	/**
	 * Retourne
	 * 
	 * @return the players
	 */
	public ArrayList<Cuby> getPlayers(int nb) {

		ArrayList<Cuby> cuby = new ArrayList<>();

		for (int i = 0; i < nb; i++) {
			cuby.add(players.get(i));
		}

		return cuby;
	}

	/**
	 * 
	 */
	private void initPlayer() {

		Cuby player1 = new Cuby(DodgeColor.BLUE);
		Cuby player2 = new Cuby(DodgeColor.PINK);

		players.add(player1);
		players.add(player2);
	}

	/**
	 * Initialisation des touches du clavier
	 */
	private void initKeyBoard() {

		// --- Cuby
		dodgeKeyboard.addCubyMove(DodgeKeyboard.ARROW_MOVE, players.get(0));
		dodgeKeyboard.addCubyMove(DodgeKeyboard.ZQSD_MOVE, players.get(1));

		// --- Quitter
		dodgeKeyboard.addKey(KeyCode.ESCAPE, new Key("Quitter", false) {

			@Override
			public void event() {
				Platform.exit();
			}

			@Override
			public void endEvent() {
				// TODO Auto-generated method stub

			}
		});
	}

	public void gameModes(ScreenName sn) {

//		switch (sn) {
//		case MULTI:
//			createPlayer(true);
//			break;
//
//		case MAP:
//
//			createPlayer(false);
//
//			viewCtrl.saveScreens(ScreenName.MAP, new MapView(this));
//			viewCtrl.goTo(ScreenName.MAP);
//
//			break;
//
//		default:
//			break;
//		}
	}

	// ---------

	public GestionnaireNiveau getGestionNiveau() { return gestionNiveau; }

	/**
	 * Retourne
	 * 
	 * @return the players
	 */
	public ArrayList<Cuby> getPlayers() { return players; }
}
