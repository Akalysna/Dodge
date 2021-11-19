package game.niveau;

import java.util.ArrayList;
import java.util.List;

public class Niveau implements Cloneable {

	private String name;
	private String cubyPath;
	private String musicPath;
	private String levelPath;

	private LevelInfo levelInfo;

	private BuildLevel build;

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

		this.build = new BuildLevel(levelPath);
	}

	/**
	 * Calcule le pourcentage de complétion du niveau
	 * 
	 * @return Entier
	 */
	public int getProgress() { return levelInfo.calculProgress(); }



	public void destroyMachine() {
		levelInfo.destroyMachine();
	}

	public void readLevel() {
		build.readLevel();

	}


	public ArrayList<Stage> getCopyStage() { return new ArrayList<Stage>(this.getStages()); }

	public String getName() { return name; }

	public String getCubyPath() { return cubyPath; }

	public String getMusicPath() { return musicPath; }

	public String getLevelPath() { return levelPath; }

	public List<Stage> getStages() { return build.getStages(); }
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
