/**
 * @author Rowena Shi
 */

// This class represent a node of the graph.
public class Node {
	
	int name;
	boolean mark;
	
	// constructor: sets node's name to name
	Node(int name) {
		this.name = name; // name of node is an integer between 0 and n-1 (n being # of vertices in graph)
	}
	
	// sets node's mark to mark
	void setMark(boolean mark) {
		this.mark = mark;
	}
	
	// returns node's mark
	boolean getMark() {
		return mark;
	}
	
	// returns node's name
	int getName() {
		return name;
	}

}
