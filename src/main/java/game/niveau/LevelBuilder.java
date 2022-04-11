/**
 * 
 */
package game.niveau;

import java.io.InputStream;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import controler.DataCtrl;
import controler.FormeManager;

/**
 * @author Llona André--Augustine
 *
 */
public class LevelBuilder {

	private ArrayList<World> levels;

	public LevelBuilder() {
		this.levels = new ArrayList<>();
		read();
	}

	/**
	 * 
	 */
	private void read() {

		InputStream is = FormeManager.class.getResourceAsStream(DataCtrl.JSON);
		if (is == null) { throw new NullPointerException("Cannot find resource file "); }

		JSONTokener tokener = new JSONTokener(is);

		// Racine du json
		JSONObject jsonRoot = new JSONObject(tokener);

		// ---

		// Tableau des formes
		JSONArray jsonWorlds = jsonRoot.getJSONArray("worlds");

		// Pour chaque monde
		for (int i = 0; i < jsonWorlds.length(); i++) {

			// Récupérer l'object "monde"
			JSONObject world = jsonWorlds.getJSONObject(i);

			String name = world.getString("worldName");
			String music = world.getString("music");
			String icon = world.getString("icon");

			this.levels.add(new World(name, icon, music));
			
			JSONArray jsonStages = world.getJSONArray("stages");
			
			
		}
	}

}
