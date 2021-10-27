package view;

import i18n.I18N;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

public class MapView extends BorderPane implements Initialisable{
	
	
	private Button btnPlayLevel; 
	private CtrlView ctrlView; 
	
	public MapView(CtrlView ctrlView) {
		this.ctrlView = ctrlView; 
	}

	@Override
	public void init() {
		
		this.btnPlayLevel = new Button(); 
		
		this.btnPlayLevel.textProperty().bind(I18N.createStringBinding("btn.map.play"));
		
	}

}
