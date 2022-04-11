/**
 * 
 */
package game.element;

/**
 * @author Llona André--Augustine
 *
 */
public interface Element {

	/** Active l'élément */
	public void active();

	/** Désactive l'élément */
	public void stop();

	/** Détruit l'élément */
	public void destroy();

}
