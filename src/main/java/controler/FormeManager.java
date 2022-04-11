package controler;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

public class FormeManager {

	private HashMap<String, ArrayList<Double>> formes;

	public FormeManager() {

		this.formes = new HashMap<>();
		read();
	}

	private void read() {

		InputStream is = FormeManager.class.getResourceAsStream(DataCtrl.JSON);
		if (is == null) { throw new NullPointerException("Cannot find resource file "); }

		JSONTokener tokener = new JSONTokener(is);

		// Racine du json
		JSONObject jsonRoot = new JSONObject(tokener);

		// ---

		// Tableau des formes
		JSONArray jsonShape = jsonRoot.getJSONArray("shapes");

		// Pour chaque element
		for (int i = 0; i < jsonShape.length(); i++) {

			// Récupérer l'object "forme"
			JSONObject shape = jsonShape.getJSONObject(i);

			// Nom de la forme
			String name = shape.getString("name");

			// Tableau des coordonnée de la forme
			JSONArray coordinate = shape.getJSONArray("coordinate");

			// Liste des points de la forme
			ArrayList<Double> points = new ArrayList<>();

			// Pour chaque valeur du tableau add list
			for (int j = 0; j < coordinate.length(); j++) {
				points.add(coordinate.getDouble(j));
			}

			// Ajout de la forme
			formes.put(name, points);
		}
	}

	public List<Double> getPoints(String key) {
		if (formes.containsKey(key))
			return formes.get(key);

		return new ArrayList<>();
	}

	public List<Double> getPoints(String key, int size) {

		ArrayList<Double> points = new ArrayList<>();

		if (formes.containsKey(key)) {

			for (Double d : formes.get(key)) {
				points.add(d * size);
			}

			return points;
		}

		return points;
	}
}
