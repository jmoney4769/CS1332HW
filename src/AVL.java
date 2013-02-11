import java.util.Collection;

public class AVL<T extends Comparable<T>> {

	private Node<T> root;
	private int size;

	/**
	 * Adds a data entry to the AVL tree
	 * 
	 * @param data
	 *            The data entry to add
	 */
	public void add(T data) {

		if (root == null) {
			root = new Node<T>(data);
			updateHeightAndBF(root);
			size++;
			return;
		}
		add(root, data, null, false);
		size++;
	}

	/**
	 * Recursive helper method for add
	 * 
	 * @param here
	 *            The current node for comparing
	 * @param data
	 *            The data to be added
	 */
	private void add(Node<T> here, T data, Node<T> prev, boolean isRight) {

		if (data == null
				|| (here.getData() != null && data.compareTo(here.getData()) > 0))
			if (here.getRight() == null) {
				here.setRight(new Node<T>(data));
				updateHeightAndBF(here.getRight());
			} else
				add(here.getRight(), data, here, true);
		else if (here.getLeft() == null) {
			here.setLeft(new Node<T>(data));
			updateHeightAndBF(here.getLeft());
		} else
			add(here.getLeft(), data, here, false);
		updateHeightAndBF(here);
		rotate(here, prev, isRight);
	}

	/**
	 * Adds each data entry from the collection to this AVL tree
	 * 
	 * @param c
	 *            The collection
	 */
	public void addAll(Collection<? extends T> c) {
		for (T i : c)
			add(i);
	}

	/**
	 * Removes a data entry from the AVL tree
	 * 
	 * Return null if the value does not exist
	 * 
	 * @param data
	 *            The data entry to be removed
	 * @return The removed data entry
	 */
	public T remove(T data) {

		return remove(root, data, null, false);
	}

	/**
	 * Recursive helper method for remove
	 * 
	 * @param here
	 *            The current node for comparing
	 * @param data
	 *            The data to remove
	 * @param prev
	 *            The node previous here
	 * @param isRight
	 *            True if here is the right of prev
	 * @return The data of the removed node
	 */
	private T remove(Node<T> here, T data, Node<T> prev, boolean isRight) {

		if (data == null && here.getData() == null) // don't do any of the next
													// stuff
			;
		else if (data == null && here.getData() != null
				|| !data.equals(here.getData())) // find node to be removed
			if (data == null
					|| (here.getData() != null && data
							.compareTo(here.getData()) > 0))
				if (here.getRight() != null) {
					T dat = remove(here.getRight(), data, here, true);
					updateHeightAndBF(here);
					rotate(here, prev, isRight);
					return dat;
				} else
					return null;
			else if (here.getLeft() != null) {
				T dat = remove(here.getLeft(), data, here, false);
				updateHeightAndBF(here);
				rotate(here, prev, isRight);
				return dat;
			} else
				return null;
		T dat = here.getData();
		if (here.getLeft() == null && here.getRight() == null) // no children
			if (prev == null)
				root = null;
			else if (isRight)
				prev.setRight(null);
			else
				prev.setLeft(null);
		else if (here.getLeft() == null ^ here.getRight() == null) // one child
			if (here.getLeft() == null)
				if (prev == null)
					root = moveNode(here.getRight(), here);
				else if (isRight)
					prev.setRight(moveNode(here.getRight(), here));
				else
					prev.setLeft(moveNode(here.getRight(), here));
			else if (prev == null)
				root = moveNode(here.getLeft(), here);
			else if (isRight)
				prev.setRight(moveNode(here.getLeft(), here));
			else
				prev.setLeft(moveNode(here.getLeft(), here));
		else // two children
		if (prev == null)
			root = moveNode(findPredecessor(here, true, true), root);
		else if (isRight)
			prev.setRight(moveNode(findPredecessor(here, true, false), here));
		else
			prev.setLeft(moveNode(findPredecessor(here, true, false), here));
		size--;
		updateHeightAndBF(here);
		rotate(here, prev, isRight);
		return dat;
	}

	/**
	 * Moves a node from its current position to the position that here is in
	 * 
	 * @param newNode
	 *            The node to be moved
	 * @param here
	 *            The node being removed
	 * @return The node that will take the place of here
	 */
	private Node<T> moveNode(Node<T> newNode, Node<T> here) {
		if (newNode.getLeft() == null) { // there cannot be anything to the
											// right

			newNode.setLeft(here.getLeft());
			if (here.getRight() != newNode)
				newNode.setRight(here.getRight());
		} else {
			here.getLeft().setRight(newNode.getLeft());
			if (here.getLeft() != newNode)
				newNode.setLeft(here.getLeft());
			if (here.getRight() != newNode)
				newNode.setRight(here.getRight());
		}
		return newNode;
	}

	/**
	 * Helper method to find the predecessor to a node
	 * 
	 * @param here
	 *            The node in question
	 * @param isFirst
	 *            True if this is the first iteration (when calling this, it
	 *            should be true)
	 * @param isRoot
	 *            True if here is the root
	 * @return The predecessor to here
	 */
	private Node<T> findPredecessor(Node<T> here, boolean isFirst,
			boolean isRoot) {
		if (isFirst) {
			if (here.getLeft().getRight() == null) {
				Node<T> temp = here.getLeft();
				if (!isRoot)
					here.setLeft(null);
				return temp;
			}
			return findPredecessor(here.getLeft(), false, isRoot);
		} else {
			if (here.getRight().getRight() == null) {
				Node<T> temp = here.getRight();
				if (!isRoot)
					here.setRight(null);
				return temp;
			}
			return findPredecessor(here.getRight(), false, isRoot);
		}
	}

	/**
	 * Checks if the AVL tree contains a data entry
	 * 
	 * @param data
	 *            The data entry to be checked
	 * @return If the data entry is in the AVL tree
	 */
	public boolean contains(T data) {
		return contains(root, data);
	}

	/**
	 * Recursive helper method for contains
	 * 
	 * @param here
	 *            The current node
	 * @param data
	 *            The data that is being looked for
	 * @return True if a node with the data is in the tree
	 */
	private boolean contains(Node<T> here, T data) {

		if (isEmpty())
			return false;
		if (data == null && here.getData() == null)
			return true;
		if (data == null || !data.equals(here.getData()))
			if (data == null
					|| (here.getData() != null && data
							.compareTo(here.getData()) > 0))
				if (here.getRight() != null)
					return contains(here.getRight(), data);
				else
					return false;
			else if (here.getLeft() != null)
				return (contains(here.getLeft(), data));
			else
				return false;
		return true;
	}

	/**
	 * Calculates the current height and balance factor for a node and updates
	 * the values
	 * 
	 * THIS DOES NOT RECURSIVELY UPDATE N AND ALL OF N'S CHILDREN, ONLY UPDATE N
	 * (caps because it's important! Don't kill the running time of everything!)
	 * 
	 * @param n
	 *            The node whose values are to be calculated and updated
	 * @return The node passed in with updated values
	 */
	private Node<T> updateHeightAndBF(Node<T> here) {

		if (here.getRight() == null && here.getLeft() == null) {// no children
			here.setHeight(1);
			here.setBf(0);
		}

		else if (here.getRight() == null) {// one child
			here.setHeight(1 + here.getLeft().getHeight());
			here.setBf(here.getLeft().getHeight());
		}

		else if (here.getLeft() == null) {
			here.setHeight(1 + here.getRight().getHeight());
			here.setBf(here.getRight().getHeight() * -1);
		}

		else {// two children
			here.setHeight(1 + Math.max(here.getLeft().getHeight(), here
					.getRight().getHeight()));
			here.setBf(-here.getRight().getHeight()
					+ here.getLeft().getHeight());
		}

		return here;
	}

	/**
	 * Determines what rotation, if any, needs to be performed on a node and
	 * does the appropriate rotation
	 * 
	 * @param n
	 *            The node to potentially be rotated
	 * @return The new root of the subtree that is now balanced due to the
	 *         rotation (possibly the same node that was passed in)
	 */
	private Node<T> rotate(Node<T> here, Node<T> prev, boolean isRight) {

		Node<T> newRoot = here;
		if (here.getBf() == -2)
			if (here.getRight().getBf() > 0)
				newRoot = rightLeft(here, prev, isRight);
			else
				newRoot = left(here, prev, isRight);
		else if (here.getBf() == 2)
			if (here.getLeft().getBf() < 0)
				newRoot = leftRight(here, prev, isRight);
			else
				newRoot = right(here, prev, isRight);
		updateHeightAndBF(here);
		return newRoot;
	}

	/**
	 * Performs a left rotation on a node
	 * 
	 * @param n
	 *            The node to have the left rotation performed on
	 * @return The new root of the subtree that is now balanced due to the
	 *         rotation
	 */
	private Node<T> left(Node<T> here, Node<T> prev, boolean isRight) {

		Node<T> newRoot = here.getRight();
		if (prev == null)
			root = here.getRight();
		else if (isRight)
			prev.setRight(newRoot);
		else
			prev.setLeft(newRoot);
		here.setRight(here.getRight().getLeft());
		newRoot.setLeft(here);
		return (prev == null) ? root : (isRight) ? prev.getRight() : prev
				.getLeft();
	}

	/**
	 * Performs a right rotation on a node
	 * 
	 * @param n
	 *            The node to have the right rotation performed on
	 * @return The new root of the subtree that is now balanced due to the
	 *         rotation
	 */
	private Node<T> right(Node<T> here, Node<T> prev, boolean isRight) {

		Node<T> newRoot = here.getLeft();
		if (prev == null)
			root = here.getLeft();
		else if (isRight)
			prev.setRight(newRoot);
		else
			prev.setLeft(newRoot);
		here.setLeft(here.getLeft().getRight());
		newRoot.setRight(here);
		return (prev == null) ? root : (isRight) ? prev.getRight() : prev
				.getLeft();
	}

	/**
	 * Performs a left right rotation on a node
	 * 
	 * @param n
	 *            The node to have the left right rotation performed on
	 * @return The new root of the subtree that is now balanced due to the
	 *         rotation
	 */
	private Node<T> leftRight(Node<T> here, Node<T> prev, boolean isRight) {
		here.setLeft(left(here.getLeft(), here, false));
		return right(here, prev, isRight);
	}

	/**
	 * Performs a right left rotation on a node
	 * 
	 * @param n
	 *            The node to have the right left rotation performed on
	 * @return The new root of the subtree that is now balanced due to the
	 *         rotation
	 */
	private Node<T> rightLeft(Node<T> here, Node<T> prev, boolean isRight) {
		here.setRight(right(here.getRight(), here, true));
		return left(here, prev, isRight);
	}

	/**
	 * Checks to see if the AVL tree is empty
	 * 
	 * @return If the AVL tree is empty or not
	 */
	public boolean isEmpty() {
		return root == null;
	}

	/**
	 * Clears this AVL tree
	 */
	public void clear() {
		root = null;
		size = 0;
	}

	/*
	 * Getters and Setters: Do not modify anything below this point
	 */

	public Node<T> getRoot() {
		return root;
	}

	public void setRoot(Node<T> root) {
		this.root = root;
	}

	public int size() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public static class Node<K extends Comparable<K>> {

		private K data;
		private Node<K> left, right;
		private int height;
		private int bf;

		public Node(K data) {
			setData(data);
		}

		public K getData() {
			return data;
		}

		public void setData(K data) {
			this.data = data;
		}

		public Node<K> getLeft() {
			return left;
		}

		public void setLeft(Node<K> left) {
			this.left = left;
		}

		public Node<K> getRight() {
			return right;
		}

		public void setRight(Node<K> right) {
			this.right = right;
		}

		public int getHeight() {
			return height;
		}

		public void setHeight(int height) {
			this.height = height;
		}

		public int getBf() {
			return bf;
		}

		public void setBf(int bf) {
			this.bf = bf;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "" + data;
		}
	}
}