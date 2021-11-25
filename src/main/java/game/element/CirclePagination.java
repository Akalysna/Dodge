package game.element;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Cette classe représente une pagination circulaire. Cette pagination n'est pas
 * interactive, c'est un appuie visuel
 * 
 * @author Llona André--Augustine
 *
 */
public final class CirclePagination {

	/** Cercles constituant la pagination */
	private ArrayList<Circle> pagination;

	/** Couleur du cercle de la page courante */
	private Color circleColorCurrentPage;

	/** Couleur du cercle des pages non-courante */
	private Color circleColorOtherPage;

	/**
	 * Nombre de pages pour la pagination. Le nombre de pages est égal au nombre de
	 * cercles.
	 */
	private int nombreDePage;

	/**
	 * Index de la page courante. Index de la page = Index du cercle dans la liste
	 * de cercle
	 */
	private int indexCurrentPage;



	/**
	 * Constructeur de la pagination. La couleur du cercle de la page est par défaut
	 * {@link Color#GRAY} et le cercle des autres pages est {@link Color#GAINSBORO}.
	 * L'index de la première page est par défaut a 0.
	 * 
	 * @param nbPage Nombre de page
	 */
	public CirclePagination(int nbPage) {
		this(nbPage, 0, Color.GRAY, Color.GAINSBORO);
	}

	/**
	 * Constructeur de la pagination. La couleur du cercle de la page est par
	 * defaut. {@link Color#GRAY} et le cercle des autres pages est
	 * {@link Color#GAINSBORO}.
	 * 
	 * @param nbPage           Nombre de page
	 * @param indexCurrentPage Index de la page courante
	 */
	public CirclePagination(int nbPage, int indexCurrentPage) {
		this(nbPage, indexCurrentPage, Color.GRAY, Color.GAINSBORO);
	}


	/**
	 * Constructeur de la pagination
	 * 
	 * @param nbPage           Nombre de page
	 * @param indexCurrentPage Index de la page courante
	 * @param hoverColor       Couleur de cercle de la page courante
	 * @param primaryColor     Couleur de cercle des autres pages
	 */
	public CirclePagination(int nbPage, int indexCurrentPage, Color hoverColor, Color primaryColor) {

		this.pagination = new ArrayList<>();

		this.nombreDePage = nbPage;
		this.indexCurrentPage = indexCurrentPage;
		this.circleColorOtherPage = primaryColor;
		this.circleColorCurrentPage = hoverColor;

		initPagination();
	}



	/**
	 * Initialise les éléments nécessaires à la pagination.
	 */
	private void initPagination() {

		// Création et ajout des cercles de pagination
		for (int i = 0; i < this.nombreDePage; i++) {

			this.pagination.add(new Circle(10, circleColorOtherPage));
		}
	}

	/**
	 * Passe à la page suivante si elle existe. L'index de la page courante est
	 * incrémenté de 1 et l'apparence est mise à jour.
	 */
	public void nextPage() {

		// Si la page courante n'est pas la derniere de la pagination
		if (indexCurrentPage < nombreDePage - 1) {
			indexCurrentPage++;
			currentPage(indexCurrentPage);
		}
	}

	/**
	 * Passe à la page précédente si elle existe. L'index de la page courante est
	 * décrémenté de 1 et l'apparence est mise à jour.
	 */
	public void previousPage() {

		if (indexCurrentPage >= 0) {
			indexCurrentPage--;
			currentPage(indexCurrentPage);
		}
	}


	/**
	 * Met à jour la pagination. La couleur des cercles de pagination est modifiée.
	 */
	public void currentPage(int index) {

		for (Circle c : pagination) {
			c.setFill(circleColorOtherPage);
		}

		pagination.get(index).setFill(circleColorCurrentPage);
	}

	/**
	 * Ajoute un cercle à la pagination et incrémente de 1 le nombre de page
	 */
	public void addPage() {
		this.pagination.add(new Circle(10, circleColorOtherPage));
		nombreDePage++;
	}

	/**
	 * Retourne les éléments de la pagination
	 * 
	 * @return Liste des cercles constituant la pagination
	 */
	public List<Circle> getPagination() { return pagination; }

}