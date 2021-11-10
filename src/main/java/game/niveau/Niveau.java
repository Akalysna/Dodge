package game.niveau;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import ctrl.CD.GameColor;
import factory.MachineFactory;
import factory.MachineFactory.TypeMachine;
import factory.ZoneFactory;
import factory.ZoneFactory.TypeZone;
import javafx.beans.binding.BooleanBinding;
import javafx.scene.paint.Color;
import node.machine.Machine;
import node.zone.Zone;

public class Niveau {

	private String name;
	private String cubyPath;
	private String musicPath;
	private String levelPath;

	private int progress;

	private List<Stage> stages;

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

		this.stages = new ArrayList<>();

		this.progress = 0;

		readLevel(levelPath);
	}

	public int calculProgress() {

		int totalMachine = 0;
		int totalMachineDestroy = 0;

		for (Stage st : stages) {
			totalMachine += st.getNbMachine();
			totalMachineDestroy += st.getMaxMachineDestroy();
		}
		
		if (totalMachine != 0)
			return totalMachineDestroy * 100 / totalMachine;
		else
			return 0;

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
					} else {
						this.stages.add(stage);
						stage = new Stage();
					}
				}

				else {

					currentColor = randomColor(colors);

					Machine m = new Machine(0, 0, 0, 0, currentColor, null);

					ArrayList<Zone> zbind = new ArrayList<>();


					for (int i = 0; i < line.length / 2; i++) {

						String[] coor = line[2 * i + 1].split(",");

						if (i == 0) {

							m = MachineFactory.get(TypeMachine.valueOf(line[2 * i].toUpperCase()),
									Integer.valueOf(coor[0]), Integer.valueOf(coor[1]), currentColor);
							stage.addMachine(m);
						} else {

							Zone z = ZoneFactory.get(TypeZone.valueOf(line[2 * i].toUpperCase()),
									Integer.valueOf(coor[0]), Integer.valueOf(coor[1]), currentColor);

							m.linkZone(z);
							zbind.add(z);

							stage.addZone(z);
						}

					}

					m.link(builBinding(zbind, zbind.size() - 1));
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

	private BooleanBinding builBinding(ArrayList<Zone> zbind, int i) {

		if (i < 0) {
			return new BooleanBinding() {

				@Override
				protected boolean computeValue() {
					return false;
				}
			};
		} else {

			return zbind.get(i).getEntered().or(builBinding(zbind, i - 1));
		}

	}


	/**
	 * Retourne une couleur en la retirant de la liste
	 * 
	 * @param list
	 * @return
	 */
	public Color randomColor(List<GameColor> list) {

		int index = new Random().nextInt(list.size());
		GameColor pickColor = list.remove(index);

		return Color.valueOf(pickColor.getColorName());
	}


	public String getName() { return name; }

	public String getCubyPath() { return cubyPath; }

	public String getMusicPath() { return musicPath; }

	public String getLevelPath() { return levelPath; }

	public List<Stage> getStages() { return stages; }


}
