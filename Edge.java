/**
 * @author Rowena Shi
 */

// This class represents an edge of the graph.
public class Edge {
	
	Node end1;
	Node end2;
	int type; // type of door (if edge represents a door)
	String label; // distinguishes between edges representing doors and corridors
	
	// constructor: sets end1, end2 and type
	Edge(Node u, Node v, int type) {
		end1 = u;
		end2 = v;
		this.type = type; // type of door (if edge represents a door)
	}
	
	// constructor: sets end1, end2, type, and label
	Edge(Node u, Node v, int type, String label) {
		end1 = u;
		end2 = v;
		this.type = type;
		this.label = label; // distinguishes between edges representing doors and corridors
	}
	
	Node firstEndpoint() {
		return end1;
	}
	
	Node secondEndpoint() {
		return end2;
	}
	
	int getType() {
		return type;
	}
	
	void setType(int newType) {
		type = newType;
	}
	
	String getLabel() {
		return label;
	}
	
	void setLabel(String newLabel) {
		label = newLabel;
	}
	
}
