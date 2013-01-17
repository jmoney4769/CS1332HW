import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;


/**
 * @author Jared Moore
 * @version Jan 17, 2013
 */
public class LinkedListTest {

	/** Method to create a LinkedList filled with data
	 * @return A LinkedList with data in it
	 */
	public LinkedList<Integer> addElements() {
		
		LinkedList<Integer> list = new LinkedList<Integer>();
		ArrayList<Integer> ints = new ArrayList<Integer>();
		for (int i = 1; i < 8; i++)
			ints.add(i);
		list.addAll(ints);
		return list;
	}
	/**
	 * Test method for {@link LinkedList#contains(java.lang.Object)}.
	 */
	@Test
	public void testContains() {
		LinkedList<Integer> list = addElements();
		assertEquals(true, list.contains(1));
		assertEquals(true, list.contains(5));
		assertEquals(true, list.contains(7));
		assertEquals(false, list.contains(12));
	}

	/**
	 * Test method for {@link LinkedList#get(int)}.
	 */
	@Test
	public void testGet() {
		LinkedList<Integer> list = addElements();
		assertEquals(new Integer(4), list.get(3));
		assertEquals(new Integer(1), list.get(0));
		assertEquals(new Integer(7), list.get(list.size() - 1));
	}

	/**
	 * Test method for {@link LinkedList#isEmpty()}.
	 */
	@Test
	public void testIsEmpty() {
		LinkedList<Integer> list = addElements();
		assertEquals(false, list.isEmpty());
		list.clear();
		assertEquals(true, list.isEmpty());
	}

	/**
	 * Test method for {@link LinkedList#remove(int)}.
	 */
	@Test
	public void testRemoveInt() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link LinkedList#remove(java.lang.Object)}.
	 */
	@Test
	public void testRemoveObject() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link LinkedList#set(int, java.lang.Object)}.
	 */
	@Test
	public void testSet() {
		fail("Not yet implemented");
	}
}
