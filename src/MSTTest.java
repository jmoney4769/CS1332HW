import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

import org.junit.Test;

/**
 * @author Jared Moore
 * @version Mar 30, 2013
 */
public class MSTTest {

	public ArrayList<Graph> makeGraphList() {
		
		ArrayList<Graph> graphs = new ArrayList<Graph>();
		String text = "";
		try {
			BufferedReader br = new BufferedReader(new FileReader("res/graphs.txt"));
			text = br.readLine();
			br.close();
		}
		catch (FileNotFoundException e) {
			System.err.println("File not found");
		}
		catch (IOException e) {
			System.err.println("Error with file");
		}
		
		Scanner input = new Scanner(text);
		input.useDelimiter("#");
		String currentGraph;
		while (input.hasNext()) {
			currentGraph = input.next();
			graphs.add(new Graph(currentGraph));
		}
	
		input.close();
		return graphs;
	}
	
	@Test
	public void testSimpleTwoVertex() {
		ArrayList<Graph> graphs = makeGraphList();
		Collection<Edge> edgeList = MST.kruskals(graphs.get(0));
		assertNotNull(edgeList);
		assertEquals(1, edgeList.size());
		assertTrue(edgeList.contains(new Edge(new Vertex(1), new Vertex(2), 1)));
	}
	
	@Test
	public void testSimpleNotConnected() {
		ArrayList<Graph> graphs = makeGraphList();
		Collection<Edge> edgeList = MST.kruskals(graphs.get(1));
		assertNull(edgeList);
	}
	
	@Test
	public void testSimpleTriangle() {
		ArrayList<Graph> graphs = makeGraphList();
		Collection<Edge> edgeList = MST.kruskals(graphs.get(2));
		assertNotNull(edgeList);
		assertEquals(2, edgeList.size());
		assertTrue(edgeList.contains(new Edge(new Vertex(1), new Vertex(2), 1)));
		assertTrue(edgeList.contains(new Edge(new Vertex(1), new Vertex(3), 2)));
	}
}
