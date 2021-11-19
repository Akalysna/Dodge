package game.niveau;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import controller.DataCtrl.GameColor;
import game.element.factory.MachineFactory;
import game.element.factory.MachineFactory.TypeMachine;
import game.element.factory.ZoneFactory;
import game.element.factory.ZoneFactory.TypeZone;
import game.element.machine.Machine;
import game.element.zone.Zone;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.animation.PathTransition.OrientationType;
import javafx.beans.binding.BooleanBinding;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;
import util.Forme;
import util.Forme.SizeShape;

public class BuildLevel {

	private List<Stage> stages;
	private String levelPath;

	private Stage stage;

	private int nbMachine;



	private ArrayList<GameColor> colors;
	private Color currentColor;

	private Forme forme;

	public BuildLevel(String path) {

		this.levelPath = path;

		this.stages = new ArrayList<>();
		this.stage = new Stage();
		this.forme = new Forme();

		this.nbMachine = 0;
		this.colors = new ArrayList<>(Arrays.asList(GameColor.values()));

		randomColor();

		readLevel();
	}


	public void readLevel() {

		stages.clear();

		boolean firstLine = true;

		try {

			String l;
			BufferedReader in = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(levelPath)));

			while ((l = in.readLine()) != null) {

				// Skip des commentaires
				if (l.charAt(0) != '#') {

					String[] line = l.split("\t");



					// Add and create stage
					if (line[0].equals("Stage")) {

						if (firstLine) {
							stage = new Stage();
							firstLine = false;
						} else {
							this.stages.add(stage);
							stage = new Stage();
						}
					} else if (l.startsWith("Cuby")) {

						String s = l.split(":")[1];
						Point2D cubyPosition = new Point2D(Integer.valueOf(s.split(",")[0]),
								Integer.valueOf(s.split(",")[1]));
						stage.setCubyPosition(cubyPosition);
					}

					else {

						ArrayList<Zone> zoneToBind = new ArrayList<>();
						Machine m = createMachine(line[0]);

						nbMachine++;

						for (int i = 1; i < line.length; i++) {

							Zone z = createZone(line[i]);
							zoneToBind.add(z);
							m.bindToDestroyMachine(z);
						}

						m.bindEnteredZone(builBinding(zoneToBind, zoneToBind.size() - 1));

					}

					randomColor();
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


	private Zone createZone(String l) {

		Zone z;
		String[] line = l.split(":");

		// Move machine
		if (TypeMachine.valueOf(line[0]).equals(TypeMachine.MOVE)) {

			z = ZoneFactory.get(TypeZone.valueOf(line[2]), forme.getPoints(line[3], SizeShape.valueOf(line[4])), 0, 0,
					currentColor);

			stage.addZone(z);
			stage.addpathTransition(getPath(z, line[1]));

		} else {

			int x = Integer.parseInt(line[3].split(",")[0]);
			int y = Integer.parseInt(line[3].split(",")[1]);
			z = ZoneFactory.get(TypeZone.valueOf(line[0]), forme.getPoints(line[1], SizeShape.valueOf(line[2])), x, y,
					currentColor);
			stage.addZone(z);

		}

		return z;

	}

	private Machine createMachine(String l) {
		Machine machine;
		String[] line = l.split(":");

		// Move machine
		if (TypeMachine.valueOf(line[0]).equals(TypeMachine.MOVE)) {

			machine = MachineFactory.get(TypeMachine.valueOf(line[2]), 0, 0, currentColor);
			machine.setMoving(true);

			stage.addMachine(machine);
			stage.addpathTransition(getPath(machine, line[1]));


		} else {

			int x = Integer.parseInt(line[1].split(",")[0]);
			int y = Integer.parseInt(line[1].split(",")[1]);
			machine = MachineFactory.get(TypeMachine.valueOf(line[0]), x, y, currentColor);



			stage.addMachine(machine);
		}
		return machine;
	}


	/**
	 * Créer un chemin
	 * 
	 * @param node
	 * @param line
	 * @return
	 */
	private PathTransition getPath(Node node, String line) {
		Path path = new Path();

		ArrayList<Double> points = forme.getPoints(line);

		for (int i = 0; i < points.size() / 2; i++) {

			// Récuperation des coordonnées de l'élément
			double x = points.get(2 * i);
			double y = points.get(2 * i + 1);

			if (i == 0)
				path.getElements().add(new MoveTo(x, y));

			else
				path.getElements().add(new LineTo(x, y));
		}



		path.getStrokeDashArray().addAll(50.0, 30.0);
		path.setStroke(Color.rgb(40, 45, 54));
		path.setStrokeWidth(5);

		PathTransition pathTransition = new PathTransition();
		pathTransition.setDuration(Duration.seconds(5));
		pathTransition.setNode(node);
		pathTransition.setPath(path);

		pathTransition.setOrientation(OrientationType.NONE);
		pathTransition.setCycleCount(Animation.INDEFINITE);
		pathTransition.setInterpolator(Interpolator.LINEAR);

		return pathTransition;
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
	 * Modifie la couleur courante. La couleur est choisi et retirer de la liste des
	 * couleurs {@link BuildLevel#colors}
	 */
	public void randomColor() {

		if (colors.isEmpty())
			this.colors = new ArrayList<>(Arrays.asList(GameColor.values()));

		int index = new Random().nextInt(colors.size());

		this.currentColor = Color.valueOf(colors.remove(index).getColorName());
	}


	public List<Stage> getStages() { return stages; }

	public String getLevelPath() { return levelPath; }

	public int getNbMachine() { return nbMachine; }

}
