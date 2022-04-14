package game.niveau;

import java.util.ArrayList;
import java.util.List;

public class World {

	/**Nom du niveau*/
	private String name;
	
	/**Chemin vers l'image du niveau*/
	private String icon;
	
	/**Chemin vers la musique du niveau*/
	private String music;
	
	/**Information sur le niveau*/
	private LevelInfo levelInfo;

	private List<Stage> stages;
	
	private int indexCurrentStage; 

	/**
	 * Constructeur de Niveau
	 * 
	 * @param name      Nom du niveau
	 * @param cubyPath  chemin d'acces de l'image du niveau
	 * @param musicPath chemin d'acces de la musique du niveau
	 * @param levelPath chemin d'acces du niveau
	 */
	public World(String name, String cubyPath, String musicPath) {

		this.name = name;
		this.icon = cubyPath;
		this.music = musicPath;

		this.levelInfo = new LevelInfo();
		
		this.stages = new ArrayList<>(); 
	}
	
	public void addStage(List<Stage> stage) {
		this.stages.addAll(stage); 
	}
	
	public Stage getStage(int index) {
		return stages.get(index);
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
	

	public int getNbStage() {
		return this.stages.size(); 
	}

	public String getName() { return name; }

	public String getCubyPath() { return icon; }

	public String getMusicPath() { return music; }

	public List<Stage> getStages() { return stages; }

	@Override
	public String toString() {
		return "World [name=" + name + ", icon=" + icon + ", music=" + music + ", levelInfo=" + levelInfo  + ", stages=" + stages + "]";
	}
	
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
