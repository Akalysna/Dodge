/**
 * 
 */
package game.niveau;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import controler.DataCtrl;
import controler.DataCtrl.TypeElement;
import controler.DodgeCtrl;
import controler.FormeManager;
import game.element.factory.MachineFactory;
import game.element.machine.Throwball;
import game.element.zone.Zone;
import util.Position;

/**
 * @author Llona André--Augustine
 *
 */
public class WorldManager {

	private ArrayList<World> worlds;
	private Stage currentStage;
	private World currentWorld;

	public WorldManager() {
		this.worlds = new ArrayList<>();
		read();
		
		if(!worlds.isEmpty()) {
			currentWorld = worlds.get(0); 
			currentStage = currentWorld.getStage(0);
		}
	}

	//----------------
	
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

			World word = new World(name, icon, music);

			JSONArray jsonStages = world.getJSONArray("stages");
			word.addStage(getStages(jsonStages));

			this.worlds.add(word);
		}
	}

	private ArrayList<Stage> getStages(JSONArray jsonStages) {

		ArrayList<Stage> stages = new ArrayList<>();

		// Pour chaque stage
		for (int i = 0; i < jsonStages.length(); i++) {

			JSONObject jStage = jsonStages.getJSONObject(i);
			double x = jStage.getJSONObject("cubyPosition").getDouble("x");
			double y = jStage.getJSONObject("cubyPosition").getDouble("y");

			Stage stage = new Stage(new Position(x, y));

			JSONArray jsonMachine = jStage.getJSONArray("throwballs");

			for (int j = 0; j < jsonMachine.length(); j++) {

				JSONObject jMachine = jsonMachine.getJSONObject(j);

				TypeElement type = TypeElement.valueOf(jMachine.getString("type"));

				x = jMachine.getJSONObject("position").getDouble("x");
				y = jMachine.getJSONObject("position").getDouble("y");
				Position cubyPos = new Position(x, y);

				Throwball m = MachineFactory.get(type, cubyPos);

				JSONArray jsonZone = jMachine.getJSONArray("zones");

				ArrayList<Zone> zones =  getZones(jsonZone , m);

				m.addZone(zones);
				stage.addThrowball(m);
			}

			stages.add(stage);
		}

		return stages;
	}

	private ArrayList<Zone> getZones(JSONArray jsonZone, Throwball m) {

		ArrayList<Zone> zones = new ArrayList<>();
		
		for (int k = 0; k < jsonZone.length(); k++) {
			JSONObject jZone = jsonZone.getJSONObject(k);

			// TODO Penser à faire une factory
			TypeElement type = TypeElement.valueOf(jZone.getString("type"));

			String shape = jZone.getString("shape");
			int size = jZone.getInt("size");

			List<Double> list = DodgeCtrl.formeManager.getPoints(shape);

			double x = jZone.getJSONObject("position").getDouble("x");
			double y = jZone.getJSONObject("position").getDouble("y");

			Position zonePos = new Position(x, y);

			Zone zone = new Zone(zonePos, list, size, m.getColor());
			zone.setMachine(m);

			zones.add(zone);
		}
		return zones; 
	}

	//----------------
	
	
	
	//----------------
	
	/**
	 * Retourne
	 * 
	 * @return the levels
	 */
	public ArrayList<World> getLevels() { return worlds; }

	public World getLevel(int index) {
		return worlds.get(index);
	}
	
	/** 
	 * Retourne
	 * @return the currentStage
	 */
	public Stage getCurrentStage() { return currentStage; }

	/** 
	 * Retourne
	 * @return the currentWorld
	 */
	public World getCurrentWorld() { return currentWorld; }

	
}