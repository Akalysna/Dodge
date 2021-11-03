package game.niveau;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ctrl.ControleurDonnee;

class NiveauTest {
	
	private Niveau lvl1;

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void test() {

		lvl1 = new Niveau(ControleurDonnee.PATH_NIVEAU + "lvl1.txt");
		assertEquals(lvl1.getStages().size(), 2);
	}

}
