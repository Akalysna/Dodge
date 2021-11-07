package ctrl;

import java.util.EnumMap;

import javafx.scene.Parent;
import javafx.scene.Scene;

public class CtrlView {
	
	public enum ScreenName {
		HOME, MAP, GAME, SETTING, MULTI, GAMEOVER; 
	}

	private Scene scene; 
	private EnumMap<ScreenName, Parent> screens; 
	
	public CtrlView(Scene scene) {
		this.scene = scene; 
		this.screens = new EnumMap<>(ScreenName.class); 
	}
	
	public void saveScreens(ScreenName sn, Parent parent) {
		this.screens.put(sn, parent); 
	}
	
	public void goToNewView(ScreenName sn, Parent parent) {
		saveScreens(sn, parent);
		goTo(sn);
	}
	
	public void goTo(ScreenName sn) {
		if(screens.containsKey(sn))
			scene.setRoot(screens.get(sn));
		
		//QUESTION Ajouter une exception ? 

	}
	
	
	

}
