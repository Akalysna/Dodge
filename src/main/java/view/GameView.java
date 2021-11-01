package view;

import app.DodgeCtrl;
import ctrl.GameCtrl;
import game.niveau.Niveau;
import javafx.scene.layout.AnchorPane;

public class GameView extends AnchorPane implements Initialisable{
	
	private DodgeCtrl dodgeCtrl;
	private GameCtrl gameCtrl; 

	private Niveau currentLevel; 
	
	private int indexCurrentStage; 

	public GameView(DodgeCtrl dodgeCtrl) {
		this.dodgeCtrl = dodgeCtrl; 
		this.currentLevel = dodgeCtrl.getCurrentLevel(); 
		this.indexCurrentStage = 0; 
		
		this.gameCtrl = new GameCtrl(this, dodgeCtrl); 
		
		init();
	}
	
	@Override
	public void init() {
		
		this.getChildren().addAll(currentLevel.getStages().get(indexCurrentStage).getNodes()); 
	}
	
	private void nextStage() {
		
		if(indexCurrentStage >= currentLevel.getStages().size()) {
			endLevel();
		} else {
			indexCurrentStage ++; 
		}

	}
	
	private void endStage() {
		// TODO Auto-generated method stub

	}
	
	private void endLevel() {
		// TODO Auto-generated method stub

	}

}
