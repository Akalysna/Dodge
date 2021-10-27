package i18n;

import java.util.Locale;
import java.util.ResourceBundle;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class I18N {

	/**
	 * Enumeration des langues du jeu
	 * @author Llona
	 *
	 */
	public enum LangueAppli {
		FRANCAIS,
		ANGLAIS; 
	}

	/**
	 * Local courant utilis� 
	 */
	private static ObjectProperty<Locale> currentLocal;

	private static Locale localFR = new Locale("fr", "FR");
	private static Locale localEN = new Locale("en", "US");

	private static String baseNameBundle = "i18n/dodge/Dodge";
	
	static {
		
		currentLocal = new SimpleObjectProperty<>(Locale.getDefault()); 
		
		//D�s que le Local courant change, le local par defaut prend cette valeur
		currentLocal.addListener((observable, oldValue, newValue) -> Locale.setDefault(newValue));
	}


	/**
	 * R�cup�re le texte associ� dans la langue courante
	 * @param key
	 * @return
	 */
	public static String get(String key) {
		
		ResourceBundle res = ResourceBundle.getBundle(baseNameBundle, currentLocal.get());
		return res.getString(key);
	}

	/**
	 * Permet de li� le texte a l'objet. Lorsque la langue courante est changer le texte le sera aussi 
	 * @param key
	 * @return
	 */
	public static StringBinding createStringBinding(String key) {
		return Bindings.createStringBinding(() -> get(key), currentLocal);
	}
	
	/**
	 * Change la langue
	 * @param langApp
	 */
	public static void setLocal(LangueAppli langApp) {
		currentLocal.set(obtenirLocal(langApp));
	}
	
	/**
	 * Renvoie le bon local selon la langue choisi
	 * @param langApp
	 * @return
	 */
	private static Locale obtenirLocal(LangueAppli langApp) {
		switch (langApp) {
		case FRANCAIS:
			return localFR;
		case ANGLAIS :
			return localEN; 
		default:
			return localFR;
		}
	}
}
