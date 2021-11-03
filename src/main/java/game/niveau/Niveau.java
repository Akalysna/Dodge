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
import javafx.scene.paint.Color;
import node.machine.Machine;
import node.zone.Zone;

public class Niveau {

	private String name;
	private String cubyPath;
	private String musicPath;
	private String levelPath;

	private List<Stage> stages;

	public Niveau(String levelPath) {
		this("", "", "", levelPath);
	}

	/**
	 * Constructeur de Niveau 
	 * @param name Nom du niveau 
	 * @param cubyPath chemin d'acces de l'image du niveau 
	 * @param musicPath chemin d'acces de la musique du niveau 
	 * @param levelPath chemin d'acces du niveau 
	 */
	public Niveau(String name, String cubyPath, String musicPath, String levelPath) {

		this.name = name;
		this.cubyPath = cubyPath;
		this.musicPath = musicPath;
		this.levelPath = levelPath;

		this.stages = new ArrayList<>();

		readLevel(levelPath);
	}

	private void readLevel(String levelPath) {
		
		ArrayList<GameColor> colors = new ArrayList<>(Arrays.asList(GameColor.values()));
		Color currentColor = null; 

		boolean firstLine = true;
		Stage stage = new Stage();
		String l;
		BufferedReader in;
		
		try {
			
			in = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(levelPath)));

			while ((l = in.readLine()) != null) {

				String[] line = l.split(":");

				// Add and create stage

				if (line[0].equals("Stage")) {
					if (firstLine) {
						stage = new Stage();
						firstLine = false;
					} else
						this.stages.add(stage);
				}

				else {
					
					currentColor = randomColor(colors);
					
					Machine m = null; 

					for (int i = 0; i < line.length / 2; i++) {

						String[] coor = line[2 * i + 1].split(",");

						if (i == 0) {

							stage.addNode(
								m =	MachineFactory.get(TypeMachine.valueOf(line[2*i].toUpperCase()), Integer.valueOf(coor[0]), Integer.valueOf(coor[1]),currentColor));	
						} else {
							Zone z = ZoneFactory.get(TypeZone.valueOf(line[2*i].toUpperCase()), Integer.valueOf(coor[0]), Integer.valueOf(coor[1]),currentColor);
							m.linkZone(z);
							stage.addNode(z);
						}
					}
				}
			}

			this.stages.add(stage);

			in.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	

	public Color randomColor(List<GameColor> list) {
		
		int index =  new Random().nextInt(list.size());
		GameColor pickColor = list.remove(index); 
		
		return Color.valueOf(pickColor.getColorName());
	}
	

	public String getName() { return name; }

	public String getCubyPath() { return cubyPath; }

	public String getMusicPath() { return musicPath; }
	
	public String getLevelPath() { return levelPath; }

	public List<Stage> getStages() { return stages; }


}
