import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Jared Moore edits done by @author Taylor Wrobel, see commit data and
 *         version history for specifics
 * @version Mar 30, 2013
 */
public class MSTTest {

	private static Map<GraphType, Graph> graphs; // Changed to Map for
													// readability. See test
													// code for example.

	private enum GraphType {
		TWO_VERTEX, TWO_UNCONNECTED_SETS, TRIANGLE, WIKIPEDIA_EXAMPLE
		// Enums represent graph type in clearer fashion
	}

	@BeforeClass
	public static void makeGraphList() {

		// Runs before all of the tests in this class. Since I/O reads have the
		// potential to be time expensive, it's best to not do it at the start
		// of every test, but rather once for the suite. This is fine in this
		// case, because the list is never modified once it is created.

		graphs = new HashMap<GraphType, Graph>();
		String text = "";
		try {
			BufferedReader br = new BufferedReader(new FileReader(
					"res/graphs.txt"));
			text = br.readLine();
			br.close();
		} catch (FileNotFoundException e) {
			System.err.println("File not found");
		} catch (IOException e) {
			System.err.println("Error with file");
		}

		Scanner input = new Scanner(text);
		input.useDelimiter("#");
		String currentGraph;
		int index = 0;
		while (input.hasNext()) {
			currentGraph = input.next();
			// Enum ordering must match up with file ordering.
			graphs.put(GraphType.values()[index], new Graph(currentGraph));
			index++;
		}

		input.close();
	}

	@Test
	public void testSimpleTwoVertex() {
		Collection<Edge> edgeList = MST.kruskals(graphs
				.get(GraphType.TWO_VERTEX)); // Much improved readability.
		assertNotNull(edgeList);
		assertEquals(1, edgeList.size());
		assertTrue(edgeList.contains(new Edge(new Vertex(1), new Vertex(2), 1)));
	}

	@Test
	public void testSimpleNotConnected() {
		Collection<Edge> edgeList = MST.kruskals(graphs
				.get(GraphType.TWO_UNCONNECTED_SETS));
		assertNull(edgeList);
	}

	@Test
	public void testSimpleTriangle() {
		Collection<Edge> edgeList = MST
				.kruskals(graphs.get(GraphType.TRIANGLE));
		assertNotNull(edgeList);
		assertEquals(2, edgeList.size());
		assertTrue(edgeList.contains(new Edge(new Vertex(1), new Vertex(2), 1)));
		assertTrue(edgeList.contains(new Edge(new Vertex(1), new Vertex(3), 2)));
	}

	@Test
	public void testWikipediaExample() {
		Collection<Edge> edgeList = MST.kruskals(graphs
				.get(GraphType.WIKIPEDIA_EXAMPLE));
		assertNotNull(edgeList);
		assertEquals(6, edgeList.size());
		assertTrue(edgeList.contains(new Edge(new Vertex(1), new Vertex(2), 7)));
		assertTrue(edgeList.contains(new Edge(new Vertex(1), new Vertex(4), 5)));
		assertTrue(edgeList.contains(new Edge(new Vertex(2), new Vertex(5), 7)));
		assertTrue(edgeList.contains(new Edge(new Vertex(5), new Vertex(3), 5)));
		assertTrue(edgeList.contains(new Edge(new Vertex(7), new Vertex(5), 9)));
		assertTrue(edgeList.contains(new Edge(new Vertex(4), new Vertex(6), 6)));
	}

	@AfterClass
	public static void cleanup() {
		// Not necessary since this test class is standalone, but good practice.
		// Frees up the graphs list to be garbage collected before other tests
		// run, if this were part of a test suite.
		graphs = null;
	}
}