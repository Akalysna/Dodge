package controler;

import java.util.ArrayList;
import java.util.List;

import control.Key;
import controler.DataCtrl.DodgeColor;
import controler.DataCtrl.ScreenName;
import game.element.Cuby;
import game.niveau.World;
import game.niveau.WorldManager;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import util.EmptyLevelException;

public class DodgeCtrl {

	private ArrayList<Cuby> players;

	private DodgeKeyboard dodgeKeyboard;
	
	public static final FormeManager formeManager = new FormeManager();
	
	private WorldManager worldManager; 


	public DodgeCtrl(Scene scene) throws EmptyLevelException {

		this.players = new ArrayList<>();

		//this.gestionNiveau = new GestionnaireNiveau();

		this.dodgeKeyboard = new DodgeKeyboard(scene);
		this.worldManager = new WorldManager();
		
		initPlayer();
		initKeyBoard();
	}
	
	/**
	 * @return 
	 * 
	 */
	public World getLevel(int index) {
		return this.worldManager.getLevel(index);
	}

	/**
	 * Retourne
	 * 
	 * @return the players
	 */
	public List<Cuby> getPlayers(int nb) {

		ArrayList<Cuby> cuby = new ArrayList<>();

		for (int i = 0; i < nb; i++) {
			cuby.add(players.get(i));
		}

		return cuby;
	}

	private void initPlayer() {

		Cuby player1 = new Cuby(DodgeColor.WHITE);
		Cuby player2 = new Cuby(DodgeColor.PINK);

		players.add(player1);
		players.add(player2);
	}
	
	/**
	 * 
	 */
//	public void setCubyKey(ArrayList<KeyCode> codes, CubyShape cubyShape) {
//		dodgeKeyboard.addCubyMove(codes,cubyShape);
//	}

	/**
	 * Initialisation des touches du clavier
	 */
	private void initKeyBoard() {

		// --- Cuby
		dodgeKeyboard.addCubyMove(DodgeKeyboard.ARROW_MOVE, players.get(0));
		dodgeKeyboard.addCubyMove(DodgeKeyboard.ZQSD_MOVE, players.get(1));

		//System.out.println(players.get(0));
		
		// --- Quitter
		dodgeKeyboard.addKey(KeyCode.ESCAPE, new Key("Quitter", false) {

			@Override
			public void event() {
				Platform.exit();
			}

			@Override
			public void endEvent() {
				// Nothing
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

	/**
	 * Retourne
	 * 
	 * @return the players
	 */
	public List<Cuby> getPlayers() { return players; }
}
