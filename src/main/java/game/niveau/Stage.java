package game.niveau;

import java.util.ArrayList;
import java.util.List;

import game.element.machine.Throwball;
import util.Position;

public class Stage {

	/** Liste des machines du stage */
	private ArrayList<Throwball> throwball;

	// TODO Ajouté dans le JSON plusieurs position pour les diffs cubys
	/** Position du cuby dans le niveau */
	private Position cubyPos;

	/**
	 * Constructeur Stage. Initialisation des listes
	 * 
	 * @param cubyPos Position des cubys pour le stage
	 */
	public Stage(Position cubyPos) {
		this.throwball = new ArrayList<>();
		this.cubyPos = cubyPos;
	}

	/**
	 * Ajout de machine dans la liste des machines du stage
	 * 
	 * @param throwball Machine à ajouté
	 */
	public void addThrowball(Throwball throwball) {
		this.throwball.add(throwball);
	}

	/**
	 * Retourne le nombre de machine dans le stage
	 * 
	 * @return int
	 */
	public int getNbThrowball() { return throwball.size(); }

	// ----

	/**
	 * Retourne la liste des machines du stage
	 * 
	 * @return Liste
	 */
	public List<Throwball> getMachines() { return throwball; }

	/**
	 * Retourne la position des cubys du stage
	 * 
	 * @return the cubyPos
	 */
	public Position getCubyPos() { return cubyPos; }

	@Override
	public String toString() {
		return "Stage [ \n\tMachines : " + throwball + "\n\tPosition cuby : " + cubyPos + "]";
	}

}
