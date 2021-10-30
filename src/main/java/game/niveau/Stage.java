package game.niveau;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javafx.geometry.Point2D;
import javafx.scene.Node;

public class Stage {
	
	private HashMap<Node, Point2D> elements; 
	private List<Node> nodes; 
	
	public Stage() {
		this.elements = new HashMap<>(); 
		this.nodes = new ArrayList<>(); 
	}
	
	public void addElement(Node n, Point2D point) {
		this.elements.put(n, point); 
	}
	
	public void addNode(Node n) {
		this.nodes.add(n); 
	}

	public List<Node> getNodes() { return nodes; }
	
	

}
