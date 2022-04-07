/**
 * 
 */
package controller;

import java.util.ArrayList;

import control.Key;
import control.Keyboard;
import controller.DataCtrl.DodgeColor;
import game.element.Cuby;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

/**
 * @author Llona André--Augustine
 *
 */
public class DodgeCtrl {
	
	private Keyboard keyboard; 
	
	private ArrayList<Cuby> cubys; 
	
	public DodgeCtrl (Scene scene) {
		
		Cuby cuby = new Cuby(DodgeColor.BLUE);
		keyboard = new Keyboard(scene);
		
		keyboard.registerKey(KeyCode.S, new Key(true) {
			
			@Override
			public void event() {
				cuby.moveToX(-1);
			}
			
			@Override
			public void endEvent() {
				// TODO Auto-generated method stub
			}
		});
	}

}
