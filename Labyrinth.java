/**
 * @author Rowena Shi
 */

import java.io.FileReader; 
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;
import java.io.BufferedReader;

// This class represents the Labyrinth.
public class Labyrinth {
	
	Graph graph; // graph object
	Node entrance; // entrance of labyrinth
	Node exit; // exit of labyrinth
	int S; // scale factors for screen display
	int A; // width of labyrinth 
	int L; // length of labyrinth
	int[] keysArray = new int[10];
	
	/* Constructor that reads the input file and builds the graph representing the labyrinth.
	 * If the input file does not exist, or the format of the input file is incorrect this
	 * method should throw a LabyrinthException */
	Labyrinth(String inputFile) {
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(inputFile));
		    S = Integer.parseInt(reader.readLine()); // reads first line and stores as String S
		    A = Integer.parseInt(reader.readLine()); // reads second line and stores as String A
		    L = Integer.parseInt(reader.readLine()); // reads third line and stores as String L
		    
		    graph = new Graph(A*L); // creates a new graph object with A*L nodes
		    
		    String keys = reader.readLine(); // reads fourth line and stores as String keys
		    for(int i = 0; i < keys.length(); i=i+2) {
		    	keysArray[i/2] = Character.getNumericValue(keys.charAt(i));
		    }
		    
		    String line = reader.readLine();
		    int row = 0;
		    while(line != null) { // iterates through lines in inputfile
		    	if(row % 2 == 0) { // row is even (rooms and corridors)
			    	for(int column = 0; column < line.length(); column++) {
			    		char current = line.charAt(column);
			    		if(current == 's') { // entrance
			    			entrance = graph.getNode((row/2)*A+(column/2));
			    		}
			    		else if(current == 'x') { // exit
			    			exit = graph.getNode((row/2)*A+(column/2));
			    		}
			    		else if(current == 'c') { // corridor linking 2 rooms in the same row
			    			graph.insertEdge(graph.getNode((row/2)*A+((column-1)/2)), graph.getNode((row/2)*A+((column+1)/2)), -1, "corridor");
			    		}
			    		else if(Character.isDigit(current)) { // edge is door
			    			graph.insertEdge(graph.getNode((row/2)*A+((column-1)/2)), graph.getNode((row/2)*A+((column+1)/2)), Character.getNumericValue(current), "door"); 
			    		}
			    	}
		    	}
		    	else { // row is odd (corridors only)
		    		for(int column = 0; column < line.length(); column++) {
		    			char current = line.charAt(column);
		    			if(current == 'c') { // corridor linking 2 rooms in different rows
			    			graph.insertEdge(graph.getNode(((row-1)/2)*A+(column/2)), graph.getNode(((row+1)/2)*A+(column/2)), -1, "corridor");
			    		}
		    			else if(Character.isDigit(current)) { // edge is door
		    				graph.insertEdge(graph.getNode(((row-1)/2)*A+(column/2)), graph.getNode(((row+1)/2)*A+(column/2)), Character.getNumericValue(current), "door");
		    			}
		    		}
		    	}
		    	line = reader.readLine(); // reads next line
		    	row++;
		    }
		    reader.close();
		}
		catch (IOException e) {
			System.out.println("Could not read input file. \n");
			e.printStackTrace();
		}
		catch (GraphException e) {
			e.printStackTrace();
		}
	}
	
	// returns a reference to the Graph object
	Graph getGraph() throws LabyrinthException {
		if(graph != null) {
			return graph;
		}
		else {
			throw new LabyrinthException("getGraph failed. \n");
		}
	}
	
	Iterator solve() { // invokes private method path 
		Stack<Node> pathStack = new Stack<Node>();
		boolean foundPath = false;
		
		try {
			foundPath = path(pathStack, entrance, keysArray); // foundPath is a boolean
		} catch (GraphException e) {
			e.printStackTrace();
		}
		
		if(foundPath) { // if path is found, return the stack
			return pathStack.iterator();
		}
		else {
			return null;
		}
	}
	
	// private recursive method that aims to find the path
	// in: vertices s and t, path stack, and array of keys
	private boolean path(Stack<Node> pathStack, Node current, int[] keys) throws GraphException {
		
		int[] keysTemp = new int[keys.length]; // makes a keysTemp array to store temporary keys
		for(int i = 0; i < keys.length; i++) {
			keysTemp[i] = keys[i]; // copies elements into keys
		}
		current.setMark(true);
		pathStack.push(current);
		if(current.getName() == exit.getName()) {
			return true;
		}
		else {
			Iterator<Edge> edgeList = graph.incidentEdges(current);
			while(edgeList.hasNext()) {
				Edge edge = edgeList.next();
				if(edge.secondEndpoint().getMark() == false) {
					for(int i = 0; i < keys.length; i++) {
						keysTemp[i] = keys[i]; // copies elements into keys
					}
					if(edge.getLabel() == "door") { // edge is door
						boolean keyWorks = false;
						for(int i = edge.getType(); i < keysTemp.length; i++) { // iterates through keys (starts from door type and goes to end of list)
							if(keysTemp[i] > 0) {
								keysTemp[i]--; // decreases the count of the key type
								keyWorks = true;
								break;
							}
						}
						if(keyWorks == true) { // key works for the door
							if(path(pathStack, edge.secondEndpoint(), keysTemp) == true) {
								return true;
							}
						}
					}
					else if(edge.getLabel() == "corridor") { // edge is corridor
						if(path(pathStack, edge.secondEndpoint(), keysTemp) == true) {
							return true;
						}
					}
				}
			}
			current.setMark(false);
			pathStack.pop();
			return false;
		}
	}
	
}
