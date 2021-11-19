package util;

import java.util.EnumMap;
import java.util.List;
import java.util.Map.Entry;

import javafx.scene.input.KeyCode;

/**
 * Cette classe permet de savoir si une touche associé a une direction a été
 * déclaché
 * 
 * @author Llona
 * @see MoveDirection
 * @see KeyEtat
 */
public class KeyTouch {

	/**
	 * Cette classe permet de savoir si une touche est pressé ou non
	 * 
	 * @author Llona
	 *
	 */
	public class KeyEtat {

		/** Code de la touche */
		private KeyCode keycode;

		/* Vrai si la touche est pressé **/
		private boolean isPressed;


		/**
		 * Contructeur de l'état de la touche. Par défaut la touche n'est pas pressé
		 * 
		 * @param keycode
		 * @param isPressed
		 */
		public KeyEtat(KeyCode keycode) {
			this.keycode = keycode;
			this.isPressed = false;
		}


		/** Retourne le code de la touche */
		public KeyCode getKeycode() { return keycode; }

		/**
		 * Retourne l'états de la touche</br>
		 * true : la touche est pressé
		 */
		public boolean isPressed() { return isPressed; }

		/**
		 * Met à jour la touche
		 * 
		 * @param keycode Nouveau code de touche
		 */
		public void setKeycode(KeyCode keycode) { this.keycode = keycode; }

		/**
		 * Met à jour l'état de la touche
		 * 
		 * @param isPressed
		 */
		public void setPressed(boolean isPressed) { this.isPressed = isPressed; }

	}


	/**
	 * Direction du Cuby
	 * 
	 * @author Llona
	 */
	public enum MoveDirection {
		UP, RIGHT, DOWN, LEFT;
	}



	/**
	 * Association des directions et de leurs touches
	 */
	private EnumMap<MoveDirection, KeyEtat> keys;

	/**
	 * Constructeur de KeyTouch
	 * 
	 * @param keycode Liste des touches pour chaque direction. </br>
	 *                Ordre des directions : 0 = Haut, 1 = Droite, 2 = Bas, 3 =
	 *                Gauche
	 */
	public KeyTouch(List<KeyCode> keycode) {

		this.keys = new EnumMap<>(MoveDirection.class);

		// Tableau des directions
		MoveDirection[] move = MoveDirection.values();

		for (int i = 0; i < keycode.size(); i++) {
			keys.put(move[i], new KeyEtat(keycode.get(i)));
		}
	}


	/**
	 * Change la touche associé a la direction
	 * 
	 * @param moveDir Direction qur laquelle appliqué la nouvelle touche
	 * @param keycode Nouvelle touche
	 */
	public void changeKey(MoveDirection moveDir, KeyCode keycode) {

		// Vérifier si la clef n'est pas déjà associé a une autre direction
		contain(keycode);

		// Sinon
		this.keys.replace(moveDir, new KeyEtat(keycode));
	}

	/**
	 * Retourne le code de la touche
	 * 
	 * @param moveDir
	 * @return {@link KeyCode}
	 */
	public KeyCode getKeyCode(MoveDirection moveDir) {
		return keys.get(moveDir).getKeycode();
	}

	/**
	 * Retourne l'état de la touche de la direction
	 * 
	 * @param md Direction {@link MoveDirection}
	 * @return true : la touche est pressé
	 */
	public boolean isPressed(MoveDirection md) {
		return keys.get(md).isPressed();
	}

	/**
	 * Met a jour l'etat de la touche associé à la direction
	 * 
	 * @param md Direction {@link MoveDirection}
	 * @param b  true : la touche est pressé
	 */
	public void setPressed(MoveDirection md, Boolean b) {
		keys.get(md).setPressed(b);
	}

	public boolean contain(KeyCode keycode) {
	
		for (Entry<MoveDirection, KeyEtat> k : keys.entrySet()) {
			if (k.getValue().getKeycode().equals(keycode))
				return true;
		}
		
		return false; 
	}
}
