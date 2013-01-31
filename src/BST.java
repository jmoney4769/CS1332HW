import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/** Binary Search Tree class
 * @author Jared Moore
 * @version Jan 26, 2013
 */
public class BST<T extends Comparable<T>> {
	
	private Node<T> root = null;
	private int size = 0;

	/**
	 * Adds a data entry to the BST
	 * 
	 * null is positive infinity
	 * 
	 * @param data The data entry to add
	 */
	public void add(T data) {
		
		if (root == null) {
			root = new Node<T>(data);
			size++;
			return;
		}
		add(root, data);
		size++;
	}
	
	/** Recursive helper method for add
	 * @param here The current node for comparing
	 * @param data The data to be added
	 */
	private void add(Node<T> here, T data) {
			
		if (data == null || (here.getData() != null && data.compareTo(here.getData()) > 0))
			if (here.getRight() == null)
				here.setRight(new Node<T>(data));
			else
				add(here.getRight(), data);
		else
			if (here.getLeft() == null)
				here.setLeft(new Node<T>(data));
			else
				add(here.getLeft(), data);
	}

	/**
	 * Adds each data entry from the collection to this BST
	 * 
	 * @param c The Collection to be added
	 */
	public void addAll(Collection<? extends T> c) {
		for (T i : c)
			add(i);
	}
	
	/**
	 * Removes a data entry from the BST
	 * 
	 * null is positive infinity
	 * 
	 * @param data The data entry to be removed
	 * @return The removed data entry (null if nothing is removed)
	 */
	public T remove(T data) {
		return remove(root, data, null, false);
	}
	
	/** Recursive helper method for remove
	 * @param here The current node for comparing
	 * @param data The data to remove
	 * @param prev The node previous here
	 * @param isRight True if here is the right of prev
	 * @return The data of the removed node
	 */
	private T remove(Node<T> here, T data, Node<T> prev, boolean isRight) {
		
		if (!data.equals(here.getData())) // find node to be removed
			if (data == null || (here.getData() != null && data.compareTo(here.getData()) > 0))
				if (here.getRight() != null)
					return remove(here.getRight(), data, here, true);
				else
					return null;
			else
				if (here.getLeft() != null)
					return (remove(here.getLeft(), data, here, false));
				else
					return null;
		T dat = here.getData();
		if (here.getLeft() == null && here.getRight() == null) // no children
			if (isRight)
				prev.setRight(null);
			else
				prev.setLeft(null);
		else if (here.getLeft() == null ^ here.getRight() == null) // one child
			if (here.getLeft() == null) 
				if (isRight)
					prev.setRight(moveNode(here.getRight(), here));
				else
					prev.setLeft(moveNode(here.getRight(), here));
			else
				if (isRight)
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
		return dat;
	}

	/** Moves a node from its current position to the position that here is in
	 * @param newNode The node to be moved
	 * @param here The node being removed
	 * @return The node that will take the place of here
	 */
	private Node<T> moveNode(Node<T> newNode, Node<T> here) {
		if (newNode.getLeft() == null) { // there cannot be anything to the right
			newNode.setLeft(here.getLeft());
			newNode.setRight(here.getRight());
		}
		else {
			here.getLeft().setRight(newNode.getLeft());
			newNode.setLeft(here.getLeft());
			newNode.setRight(here.getRight());
		}
		return newNode;
	}

	/** Helper method to find the predecessor to a node
	 * @param here The node in question
	 * @param isFirst True if this is the first iteration (when calling this, it should be true)
	 * @param isRoot True if here is the root
	 * @return The predecessor to here
	 */
	private Node<T> findPredecessor(Node<T> here, boolean isFirst, boolean isRoot) {
		if (isFirst) {
			if (here.getLeft().getRight() == null) {
				Node<T> temp = here.getLeft();
				if (!isRoot)
					here.setLeft(null);
				return temp;
			}
			return findPredecessor(here.getLeft(), false, isRoot);
		}
		else {
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
	 * Checks if the BST contains a data entry
	 * 
	 * null is positive infinity
	 * 
	 * @param data The data entry to be checked
	 * @return If the data entry is in the BST 
	 */
	public boolean contains(T data) {
		return contains(root, data);
	}
	
	/** Recursive helper method for contains
	 * @param here The current node
	 * @param data The data that is being looked for
	 * @return True if a node with the data is in the tree
	 */
	private boolean contains(Node<T> here, T data) {
		
		if (data == null && here.getData() == null)
			return true;
		if (data == null || !data.equals(here.getData()))
			if (data == null || (here.getData() != null && data.compareTo(here.getData()) > 0))
				if (here.getRight() != null)
					return contains(here.getRight(), data);
				else
					return false;
			else
				if (here.getLeft() != null)
					return (contains(here.getLeft(), data));
				else
					return false;
		return true;
	}

	/**
	 * Finds the pre-order traversal of the BST
	 * 
	 * @return A list of the data set in the BST in pre-order
	 */
	public List<T> preOrder() {
		List<T> list = new LinkedList<T>();
		preOrder(root, list);
		return list;
	}

	/** Recursive helper method for preOrder
	 * @param here The current node
	 * @param list The list to add information to
	 */
	private void preOrder(Node<T> here, List<T> list) {
		
		if (here != null) {
			list.add(here.getData());
			preOrder(here.getLeft(), list);
			preOrder(here.getRight(), list);
		}
		
	}

	/**
	 * Finds the in-order traversal of the BST
	 * 
	 * @return A list of the data set in the BST in in-order
	 */
	public List<T> inOrder() {
		
		List<T> list = new LinkedList<T>();
		inOrder(root, list);
		return list;
	}
	
	/** Recursive helper method for inOrder
	 * @param here The current node
	 * @param list The list to add information to
	 */
	private void inOrder(Node<T> here, List<T> list) {
		
		if (here != null) {
			inOrder(here.getLeft(), list);
			list.add(here.getData());
			inOrder(here.getRight(), list);
		}		
	}

	/**
	 * Finds the post-order traversal of the BST
	 * 
	 * @return A list of the data set in the BST in post-order
	 */
	public List<T> postOrder() {
		
		List<T> list = new LinkedList<T>();
		postOrder(root, list);
		return list;
	}
	
	/** Recursive helper method for postOrder
	 * @param here The current node
	 * @param list The list to add information to
	 */
	private void postOrder(Node<T> here, List<T> list) {
		
		if (here != null) {
			postOrder(here.getLeft(), list);
			postOrder(here.getRight(), list);
			list.add(here.getData());
		}	
	}

	/**
	 * Checks to see if the BST is empty
	 * 
	 * @return If the BST is empty or not
	 */
	public boolean isEmpty() {
		return root == null;
	}
	
	/**
	 * Clears this BST
	 */
	public void clear() {
		size = 0;
		root = null;
	}
	
	/**
	 * @return the size of this BST
	 */
	public int size() {
		return size;
	}
	
	/**
	 * First clears this BST, then reconstructs the BST that is
	 * uniquely defined by the given preorder and inorder traversals
	 * 
	 * (When you finish, this BST should produce the same preorder and
	 * inorder traversals as those given)
	 * 
	 * @param preorder a preorder traversal of the BST to reconstruct
	 * @param inorder an inorder traversal of the BST to reconstruct
	 */
	public void reconstruct(List<? extends T> preorder, List<? extends T> inorder) {
		
		clear();
		addAll(preorder);
	}
	
	/*
	 * The following methods are for grading, do not modify them
	 */
	
	public Node<T> getRoot() {
		return root;
	}

	public void setRoot(Node<T> root) {
		this.root = root;
	}
	
	public void setSize(int size) {
		this.size = size;
	}

	public static class Node<K extends Comparable<K>> {
		
		private K data;
		private Node<K> left, right;
		
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
		
		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return data.toString();
		}
	}

}