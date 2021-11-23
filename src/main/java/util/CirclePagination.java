package util;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class CirclePagination {

	private ArrayList<Circle> pagination;

	private Color hoverColor;
	private Color primaryColor;

	private int nbPage;
	private int indexCurrentPage;

	public CirclePagination(int nbPage) {
		this(nbPage, 0, Color.GRAY, Color.GAINSBORO);
	}

	public CirclePagination(int nbPage, int indexCurrentPage) {
		this(nbPage, indexCurrentPage, Color.GRAY, Color.GAINSBORO);
	}


	public CirclePagination(int nbPage, int indexCurrentPage, Color hoverColor, Color primaryColor) {

		this.pagination = new ArrayList<>();
		this.nbPage = nbPage;
		this.hoverColor = hoverColor;
		this.primaryColor = primaryColor;
		this.indexCurrentPage = indexCurrentPage;

		initPagination();
	}


	/**
	 * Initialise les éléments necessaire à la pagination
	 */
	private void initPagination() {

		// Création et ajout des cercles de pagination
		for (int i = 0; i < this.nbPage; i++) {

			this.pagination.add(new Circle(10, primaryColor));
		}
	}
	
	

	public void nextPage() {

		if (indexCurrentPage < nbPage-1) {
			indexCurrentPage++;
			currentPage(indexCurrentPage);
		}
	}

	public void previousPage() {

		if (indexCurrentPage >= 0) {
			indexCurrentPage--;
			currentPage(indexCurrentPage);
		}
	}


	/**
	 * Met à jour la pagination
	 */
	public void currentPage(int index) {

		for (Circle c : pagination) {
			c.setFill(primaryColor);
		}

		pagination.get(index).setFill(hoverColor);
	}

	public void addPage() {
		this.pagination.add(new Circle(10, primaryColor));
	}

	public List<Circle> getPagination() { return pagination; }

}