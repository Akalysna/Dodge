package game.view;

import controller.DodgeCtrl;
import javafx.scene.layout.BorderPane;

public abstract class DodgeView extends BorderPane implements Initialisable{
	
	/**Controleur principal du jeu*/
	protected DodgeCtrl dodgeCtrl;

	protected DodgeView(DodgeCtrl dodgeCtrl) {
		this.dodgeCtrl = dodgeCtrl;
	}
	
	protected abstract void initialization(); 
	
	protected abstract void design(); 
	
	protected abstract void events(); 
}
