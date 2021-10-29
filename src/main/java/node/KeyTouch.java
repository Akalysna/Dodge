package node;

import java.util.EnumMap;
import java.util.List;

import javafx.scene.input.KeyCode;

public class KeyTouch {

	public class KeyEtat {
		
		private KeyCode keycode;
		private boolean isPressed;

		public KeyEtat(KeyCode keycode, boolean isPressed) {
			this.keycode = keycode;
			this.isPressed = isPressed;
		}

		public KeyCode getKeycode() { return keycode; }

		public void setKeycode(KeyCode keycode) { this.keycode = keycode; }

		public boolean isPressed() { return isPressed; }

		public void setPressed(boolean isPressed) { this.isPressed = isPressed; }

	}

	public enum MoveDirection {
		UP, RIGHT, DOWN, LEFT;
	}

	private EnumMap<MoveDirection, KeyEtat> keys;

	public KeyTouch(List<KeyCode> keycode) {
		this.keys = new EnumMap<>(MoveDirection.class);

		MoveDirection[] move = MoveDirection.values();
		for (int i = 0; i < keycode.size(); i++) {
			keys.put(move[i], new KeyEtat(keycode.get(i), false));
		}
	}

	public void changeKey(MoveDirection moveDir, KeyCode keycode) {
		KeyEtat ke = keys.get(moveDir);
		ke.setKeycode(keycode);
		this.keys.replace(moveDir, ke);
	}

	public KeyCode getKeyCode(MoveDirection moveDir) {
		return keys.get(moveDir).getKeycode();
	}

	public boolean isPressed(MoveDirection md) {
		return keys.get(md).isPressed();
	}

	public void setPressed(MoveDirection md, Boolean b) {
		keys.get(md).setPressed(b);
	}

}
