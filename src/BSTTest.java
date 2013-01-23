import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;


/**
 * @author Jared Moore
 * @version Jan 22, 2013
 */
public class BSTTest {

	public BST<Integer> buildTree() {
		
		ArrayList<Integer> array = new ArrayList<Integer>();
		array.add(50);
		array.add(25);
		array.add(75);
		array.add(13);
		array.add(87);
		BST<Integer> tree = new BST<Integer>();
		tree.addAll(array); // note that my addAll will call add
		return tree;
	}

	/**
	 * Test method for {@link BST#remove(java.lang.Comparable)}.
	 */
	@Test
	public void testRemove() {
		
		BST<Integer> tree = buildTree();
		assertEquals(13, (int) tree.remove(13)); // no children
		assertEquals(null, tree.getRoot().getLeft().getLeft());
		
		tree.remove(75); // one child
		assertEquals(87, (int) tree.getRoot().getRight().getData());
		
		tree.remove(50);
		assertEquals(25, (int) tree.getRoot().getData());
	}

	/**
	 * Test method for {@link BST#contains(java.lang.Comparable)}.
	 */
	@Test
	public void testContains() {
		
		BST<Integer> tree = buildTree();
		assertEquals(true, tree.contains(50));
		assertEquals(false, tree.contains(100));
		assertEquals(false, tree.contains(null));
		assertEquals(true, tree.contains(13));
	}

	/**
	 * Test method for {@link BST#preOrder()}.
	 */
	@Test
	public void testPreOrder() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link BST#inOrder()}.
	 */
	@Test
	public void testInOrder() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link BST#postOrder()}.
	 */
	@Test
	public void testPostOrder() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link BST#reconstruct(java.util.List, java.util.List)}.
	 */
	@Test
	public void testReconstruct() {
		fail("Not yet implemented"); // TODO
	}

}
