import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;


/**
 * @author Jared Moore
 * @version Jan 17, 2013
 */
public class TwistListTest {

	public TwistList<Integer> addElements() {
		
		TwistList<Integer> list = new TwistList<Integer>();
		ArrayList<Integer> ints = new ArrayList<Integer>();
		for (int i = 1; i < 10; i += 2)
			ints.add(i);
		list.addAll(ints);
		return list;
	}
	/**
	 * Test method for {@link TwistList#add(java.lang.Comparable)}.
	 */
	@Test
	public void testAdd() {
		
		TwistList<Integer> list = addElements();
		list.add(0);
		list.add(4);
		list.add(12);
		
		assertEquals(new Integer(0), list.get(0)); // beginning
		assertEquals(new Integer(4), list.get(3)); // middle
		assertEquals(new Integer(12), list.get(list.size() - 1)); // end
	}

	/**
	 * Test method for {@link TwistList#reverse(int, int)}.
	 */
	@Test
	public void testReverse() {
		TwistList<Integer> list = addElements();
		
		list.reverse(1, 2); // even number of elements
		assertEquals("[ 1 5 3 7 9 ]", list.toString());
		list.reverse(0, list.size() - 1); // whole list
		assertEquals("[ 9 7 3 5 1 ]", list.toString());
		list.reverse(1, 3); // odd number of elements
		assertEquals("[ 9 5 3 7 1 ]", list.toString());
	}

	/**
	 * Test method for {@link TwistList#flipFlop(int)}.
	 */
	@Test
	public void testFlipFlop() {
		
		TwistList<Integer> list = addElements();
		
		// original list [1 3 5 7 9]
		list.flipFlop(1);
		assertEquals("[ 5 7 9 1 3 ]", list.toString());
	}

	/**
	 * Test method for {@link TwistList#swing(int)}.
	 */
	@Test
	public void testSwing() {
		
		TwistList<Integer> list = addElements();
		
		// original list [1 3 5 7 9]
		list.swing(2);
		assertEquals("[ 5 3 1 9 7 ]", list.toString());
		list = addElements();
		list.swing(list.size() - 1);
		assertEquals("[ 9 7 5 3 1 ]", list.toString());
	}

}
