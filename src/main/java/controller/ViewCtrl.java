package controller;

import java.util.EnumMap;

import game.view.Initialisable;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class ViewCtrl {
	
	public enum ScreenName {
		HOME, MAP, GAME, SETTING, MULTI, GAMEOVER; 
	}

	private Scene scene; 
	private EnumMap<ScreenName, Initialisable> screens; 
	
	public ViewCtrl(Scene scene) {
		this.scene = scene; 
		this.screens = new EnumMap<>(ScreenName.class); 
	}
	
	public void saveScreens(ScreenName sn, Initialisable parent) {
		this.screens.put(sn, parent); 
	}
	
	public void loadAndGoto(ScreenName sn) {
		if(screens.get(sn) != null) {
			screens.get(sn).load();
			goTo(sn);
		}
	}
	
	public void saveAndGoto(ScreenName sn, Initialisable parent) {
		saveScreens(sn, parent);
		goTo(sn);
	}
	
	public void goTo(ScreenName sn) {
		if(screens.containsKey(sn))
			scene.setRoot((Parent) screens.get(sn));
		
		//QUESTION Ajouter une exception ? 

	}
	
	
	

}
