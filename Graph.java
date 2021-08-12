/**
 * @author Rowena Shi
 */

import java.util.ArrayList;
import java.util.Iterator;

// This class represents an undirected graph.
public class Graph implements GraphADT {
	
	Node[] nodes;
	Edge[][] edges;
	int edgeWidth;
	
	/* Constructor of graph. Creates an array of nodes and an empty matrix of edges. */
	Graph(int n) {
		nodes = new Node[n]; // makes nodes an empty array of size n
		edges = new Edge[n][n]; // makes edges and empty matrix of size
		for(int i = 0; i < n; i++) { // fills the nodes array with node objects
			nodes[i] = new Node(i);
		}
		edgeWidth = n;
	}
	
	/* Adds to the graph an edge connecting nodes u and v. The type for this new edge 
    is as indicated by the last parameter. This method throws a GraphException if 
	either node does not exist or if there is already an edge connecting the given vertices. */
	public void insertEdge(Node u, Node v, int edgeType) throws GraphException {
		try {
			edges[u.getName()][v.getName()] = new Edge(u, v, edgeType); // inserts an edge in the matrix (u, v)
			edges[v.getName()][u.getName()] = new Edge(v, u, edgeType); // inserts an edge in the matrix (v, u)
		}
		catch(Exception e) {
			throw new GraphException("insertEdge failed. \n");
		}
	}
	
	/* Adds to the graph an edge connecting nodes u and v. The type and label for this new edge 
    is as indicated by the last parameters. This method throws a GraphException if either node 
	does not exist or if there is already an edge connecting the given vertices. */
	public void insertEdge(Node u, Node v, int edgeType, String label) throws GraphException {
		try {
			edges[u.getName()][v.getName()] = new Edge(u, v, edgeType, label); // inserts an edge in the matrix
			edges[v.getName()][u.getName()] = new Edge(v, u, edgeType, label); // inserts an edge in the matrix
		}
		catch(Exception e) {
			throw new GraphException("insertEdge failed. \n");
		}
	}
	
	/* Returns the node with the specified name. If no node with this name exists in the graph, the 
    method throws a GraphException. */
	public Node getNode(int u) throws GraphException {
		try {
			return nodes[u];
		}
		catch(Exception e) {
			throw new GraphException("getNode failed. \n");
		}
	}
	
	/* Returns a Java Iterator storing all the edges incident on node u. It returns null if 
    node u does not have any edges incident on it. A GraphException is thrown if u is not
	a node of this graph. */
	public Iterator incidentEdges(Node u) throws GraphException {
		try {
			int j = 0;
			ArrayList<Edge> edgeList = new ArrayList<Edge>();
			while(j < edgeWidth) {
				if(edges[u.getName()][j] != null) {
					edgeList.add(edges[u.getName()][j]); // adds edge object into edgeList
				}
				j++;
			}
			return edgeList.iterator(); // returns iterator
		}
		catch(Exception e) {
			throw new GraphException("incidentEdges failed. \n");
		}
	}
	
	/* Returns the edge connecting nodes u and v. Throws a GraphException if u or v are not nodes
    of this graph or if there is no edge between u and v. */
	public Edge getEdge(Node u, Node v) throws GraphException {
		try {
			return edges[u.getName()][v.getName()];
		}
		catch(Exception e) {
			throw new GraphException("getEdge failed. \n");
		}
	}
	
	/* Returns true if nodes u and v are adjacent; returns false otherwise. Throws a GraphException
    if u or v are not nodes of this graph. */ 
	public boolean areAdjacent(Node u, Node v) throws GraphException {
		if(edges[u.getName()][v.getName()] != null) {
			return true;
		}
		else {
			return false;
		}
	}
}
