package view;

import app.DodgeCtrl;
import javafx.scene.layout.AnchorPane;

public class GameView extends AnchorPane implements Initialisable{
	
	private DodgeCtrl dodgeCtrl; 

	public GameView(DodgeCtrl dodgeCtrl) {
		this.dodgeCtrl = dodgeCtrl; 
	}
	
	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

}
