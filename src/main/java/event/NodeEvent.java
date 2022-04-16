/**
 * 
 */
package event;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.scene.Node;

/**
 * @author Llona André--Augustine
 *
 */
public class NodeEvent extends Event {

	private List<Node> element;
	private boolean remove;

	public NodeEvent(boolean b, Node... element) {
		this.element = new ArrayList<>(Arrays.asList(element));
		remove = b;
	}
	
	public NodeEvent(boolean b,  List<Node> element) {
		this.element = element;
		remove = b;
	}

	/**
	 * Retourne
	 * 
	 * @return the element
	 */
	public List<Node> getElement() { return element; }

	public boolean isRemove() { return remove; }
}
