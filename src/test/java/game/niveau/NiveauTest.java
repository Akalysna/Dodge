package game.niveau;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import controller.DataCtrl;
import javafx.embed.swing.JFXPanel;

class NiveauTest {

	final JFXPanel fxPanel = new JFXPanel();

	private Niveau lvl1;

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void test() {


		lvl1 = new Niveau(DataCtrl.PATH_NIVEAU + "lvl1.txt");
		assertEquals(2, lvl1.getStages().size());
		
		lvl1.getStages().get(0).setMachineDestroy();
		
		int purcent = lvl1.getProgress();
		
		Logger l = Logger.getLogger("");
		l.log(Level.FINE, purcent + "");
		System.out.println(purcent);
		
		assertEquals(1*100/3, purcent);
	}

}
