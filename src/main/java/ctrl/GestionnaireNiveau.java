package ctrl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import app.DodgeCtrl;
import game.niveau.Niveau;
import game.niveau.Stage;
import util.EmptyLevelException;

public class GestionnaireNiveau {

	private Stage currentStage;
	private Niveau currentLevel;
	private List<Niveau> niveaux;

	public GestionnaireNiveau() throws EmptyLevelException {
		this.setCurrentLevel(null);
		this.currentStage = null;
		this.niveaux = new ArrayList<>();

		uploadLevel();
	}

	public boolean isEmpty() { return this.niveaux.isEmpty(); }

	public Niveau getCurrentLevel() { return currentLevel; }

	public void setCurrentLevel(Niveau currentLevel) { this.currentLevel = currentLevel; }

	public void setCurrentLevel(int index) {
		if (index >= 0 && index <= niveaux.size() - 1)
			this.currentLevel = niveaux.get(index);
	}

	private void uploadLevel() throws EmptyLevelException {

		String l;
		BufferedReader in;

		try {

			in = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/niveau/config_niveau.txt")));

			while ((l = in.readLine()) != null) {

				String[] line = l.split(":");

				Niveau n = new Niveau(line[0], CD.PATH_CUBY + line[1], line[2], CD.PATH_NIVEAU + line[3]);

				this.niveaux.add(n);
			}

			in.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// TODO Lever une exception si la liste est vide, s'il n'y a aucun niveau de lu



		if (!niveaux.isEmpty())
			setCurrentLevel(0);
		else
			throw new EmptyLevelException("Aucun niveau n'a pu être lu");
	}

	/**
	 * Passe au niveau suivant s'il existe
	 * 
	 * @return true : le niveau a été changer, false : il n'y a pas de niveau
	 *         suivant
	 */
	public boolean nextLevel() {

		int index = this.niveaux.indexOf(currentLevel);
		if (index < niveaux.size() - 1) {
			currentLevel = niveaux.get(index + 1);
			return true;
		}

		return false;

	}

	/**
	 * Passe au niveau precedent s'il existe
	 * 
	 * @return Niveau precedent
	 * @see DodgeCtrl#currentLevel
	 */
	public boolean lastLevel() {

		if (currentLevel != null) {

			int index = this.niveaux.indexOf(currentLevel);

			if (index > 0) {
				currentLevel = niveaux.get(index - 1);
				return true;
			}
		}

		return false;

		// TODO Ajouter une exception ?

	}

	public boolean isFirstLevel() { return this.niveaux.indexOf(currentLevel) == 0; }

	public boolean isLastLevel() { return this.niveaux.indexOf(currentLevel) == niveaux.size() - 1; }

	public int nbLevel() {
		return this.niveaux.size();
	}

	public int indexCurentLevel() {
		return this.niveaux.indexOf(currentLevel);
	}


	// **** Stage ****

	public int indexCurentStage() {
		return this.getStageOfCurrentLevel().indexOf(currentStage);
	}

	public List<Stage> getStageOfCurrentLevel() { return this.currentLevel.getStages(); }

	public boolean nextStage() {

		if (indexCurentStage() > getStageOfCurrentLevel().size() - 1) {
			return false;
		} else {
			currentStage = getStageOfCurrentLevel().get(indexCurentLevel() + 1);
			return true;
		}

	}

	public int nbStage() {
		return getStageOfCurrentLevel().size();
	}

	public Stage getCurrentStage() { return currentStage; }

	public void setCurrentStage(Stage stage) { this.currentStage = stage; }

	public void setCurrentStage(int index) {
		if (index >= 0 && index <= nbStage() - 1)
			this.currentStage = getStageOfCurrentLevel().get(index);
	}

}
