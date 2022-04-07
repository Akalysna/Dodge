/**
 * 
 */
package controler;

import java.util.ArrayList;
import java.util.Arrays;

import control.Key;
import control.Keyboard;
import game.element.Cuby;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

/**
 * @author Llona André--Augustine
 *
 */
public class DodgeKeyboard {

	private Keyboard keyboard;

	/** Touche fléché */
	public final static ArrayList<KeyCode> ARROW_MOVE = new ArrayList<>(
			Arrays.asList(KeyCode.UP, KeyCode.DOWN, KeyCode.LEFT, KeyCode.RIGHT));

	/** Touche ZQSD */
	public final static ArrayList<KeyCode> ZQSD_MOVE = new ArrayList<>(
			Arrays.asList(KeyCode.Z, KeyCode.D, KeyCode.S, KeyCode.Q));

	public DodgeKeyboard(Scene scene) {
		keyboard = new Keyboard(scene);
	}

	/**
	 * Ajoute un event de mouvement au cuby selon sa direction
	 * 
	 * @param code Haut - Bas - Gauche - Droite
	 * @param cuby Cuby
	 */
	public void addCubyMove(ArrayList<KeyCode> code, Cuby cuby) {

		/*
		 * 0 -1		Haut
		 * 0 +1		Bas
		 * -1 0		Gauche
		 * +1 0		Droite
		 * 
		 * */
		
		int[] vector = { -1, +1 };

		int i = 0;
		int mod = 0;

		for (KeyCode c : code) {

			mod = vector[i % 2];

			int x = i < 2 ? 0 : mod;
			int y = i < 2 ? mod : 0;
			
			keyboard.registerKey(c, new Key(c.getName(), true) {

				@Override
				public void event() {
					cuby.move(x, y);
				}

				@Override
				public void endEvent() {
					// TODO Auto-generated method stub
				}
			});

			i++;
		}
	}

	/**
	 * @param key
	 * @param down
	 * 
	 */
	public void addKey(KeyCode code, Key key) {
		keyboard.registerKey(code, key);
	}

}
