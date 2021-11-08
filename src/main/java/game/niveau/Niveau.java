package game.niveau;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import ctrl.ControleurDonnee.GameColor;
import factory.MachineFactory;
import factory.MachineFactory.TypeMachine;
import factory.ZoneFactory;
import factory.ZoneFactory.TypeZone;
import javafx.beans.binding.BooleanBinding;
import javafx.scene.paint.Color;
import node.machine.Machine;
import node.zone.Zone;

public class Niveau implements Cloneable {

	private String name;
	private String cubyPath;
	private String musicPath;
	private String levelPath;

	private LevelInfo levelInfo;

	private List<Stage> stages;

	/**
	 * Constructeur de Niveau
	 * 
	 * @param levelPath
	 */
	public Niveau(String levelPath) {
		this("", "", "", levelPath);
	}

	/**
	 * Constructeur de Niveau
	 * 
	 * @param name      Nom du niveau
	 * @param cubyPath  chemin d'acces de l'image du niveau
	 * @param musicPath chemin d'acces de la musique du niveau
	 * @param levelPath chemin d'acces du niveau
	 */
	public Niveau(String name, String cubyPath, String musicPath, String levelPath) {

		this.name = name;
		this.cubyPath = cubyPath;
		this.musicPath = musicPath;
		this.levelPath = levelPath;

		this.levelInfo = new LevelInfo();

		this.stages = new ArrayList<>();

		readLevel();
	}

	/**
	 * Calcule le pourcentage de complétion du niveau
	 * 
	 * @return Entier
	 */
	public int getProgress() { return levelInfo.calculProgress(); }

	public void readLevel() {
		
		stages.clear();

		ArrayList<GameColor> colors = new ArrayList<>(Arrays.asList(GameColor.values()));

		boolean firstLine = true;
		Stage stage = new Stage();

		try {

			String l;
			BufferedReader in;
			in = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(levelPath)));

			while ((l = in.readLine()) != null) {

				String[] line = l.split(":");

				// Add and create stage
				if (line[0].equals("Stage")) {

					if (firstLine) {
						stage = new Stage();
						firstLine = false;
					} else {
						this.stages.add(stage);
						stage = new Stage();
					}
				}

				else
					createNode(line, stage, randomColor(colors));

			}

			this.stages.add(stage);

			in.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Lit la ligne passer en parametre est construit les éléments
	 * 
	 * @param line   Ligne à traiter
	 * @param stage  Stage auquel ajouter les éléments
	 * @param colors
	 */
	private void createNode(String[] line, Stage stage, Color color) {

		ArrayList<Zone> zoneToBind = new ArrayList<>();

		Machine m = null;

		// Avancer de deux en deux
		for (int i = 0; i < line.length / 2; i++) {

			// Récuperation des coordonnées de l'élément
			int x = Integer.parseInt(line[2 * i + 1].split(",")[0]);
			int y = Integer.parseInt(line[2 * i + 1].split(",")[1]);

			String type = line[2 * i].toUpperCase(); // Type de l'élément

			if (i == 0) { // C'est la machine

				m = MachineFactory.get(TypeMachine.valueOf(type), x, y, color);
				stage.addMachine(m);
				levelInfo.addMachine();

			} else { // Sinon c'est une zone

				Zone z = ZoneFactory.get(TypeZone.valueOf(type), x, y, color);

				m.bindToDestroyMachine(z);

				zoneToBind.add(z);
				stage.addZone(z);
			}
		}

		if (m != null)
			m.bindEnteredZone(builBinding(zoneToBind, zoneToBind.size() - 1));
	}


	public void destroyMachine() {
		levelInfo.destroyMachine();
	}

	/**
	 * Renvoie un BooleanBinding. Lie tous les propriétés Entered de toutes les
	 * zones de la liste </br>
	 * Méthode récursif
	 * 
	 * @param list Liste des zone tester
	 * @param i    Taille de la liste
	 * @return
	 */
	private BooleanBinding builBinding(ArrayList<Zone> list, int i) {

		if (i < 0) {
			return new BooleanBinding() {

				@Override
				protected boolean computeValue() {
					return false;
				}
			};

		} else
			return list.get(i).getEntered().or(builBinding(list, i - 1));
	}

	/**
	 * Retourne une couleur en la retirant de la liste
	 * 
	 * @param list Liste des couleurs
	 * @return Color
	 */
	public Color randomColor(List<GameColor> list) {

		int index = new Random().nextInt(list.size());
		GameColor pickColor = list.remove(index);

		return Color.valueOf(pickColor.getColorName());
	}

	public ArrayList<Stage> getCopyStage() { return new ArrayList<Stage>(this.getStages()); }

	public String getName() { return name; }

	public String getCubyPath() { return cubyPath; }

	public String getMusicPath() { return musicPath; }

	public String getLevelPath() { return levelPath; }

	public List<Stage> getStages() { return stages; }
}

class LevelInfo {
	private int totalMachine = 0;
	private int totalMachineDestroy = 0;

	private int progress;

	public void addMachine() {
		this.totalMachine++;
	}

	public void destroyMachine() {
		totalMachineDestroy++;
	}

	public int calculProgress() {

		if (totalMachine != 0) {
			int moy = totalMachineDestroy * 100 / totalMachine;
			this.progress = moy > this.progress ? moy : this.progress;
			return this.progress;
		} else
			return 0;
	}
}
