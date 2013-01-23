/**
 * This class extends LinkedList, but there's a twist. Read the documentation
 * for each method. Note that the data here is Comparable.
 */
public class TwistList<E extends Comparable<E>> extends LinkedList<E> {

	/**
	 * Add a piece of data either at the front of the list if the data
	 * is less than the head. If the data to be added is not less then 
	 * the data at the front of the list then find the first place in the
	 * list where the data is between two other points of data. If this is
	 * never true then place the new piece of data at the end of the list.
	 * 
	 * Last of all call swing with the index at which the new piece of data was added.
	 */
	@Override
	public void add(E e) {
		
		if (head == null) {
			super.add(e);
			return;
		}
		int indexAdded = 0;
		Node<E> here = head, addNode = new Node<E>(e);
		
		while (true) {
			if (here == head && e.compareTo(here.getData()) < 0)
				break;
			else if (e.compareTo(here.getData()) > 0 && e.compareTo(here.getNext().getData()) < 0) 
				break;
			else if (indexAdded == size)
				break;
			else {
				here = here.getNext();
				indexAdded++;
			}
		}
		
		if (indexAdded == 0) {
			addNode.setNext(head);
			here = head;
			for (int i = 0; i < size - 1; i++)
				here = here.getNext();
			here.setNext(addNode);
			head = addNode;
		}
		else {
			here = head;
			for (int i = 0; i < indexAdded - 1; i++)
				here = here.getNext();
			addNode.setNext(here.getNext());
			here.setNext(addNode);
		}
		size++;
		swing(indexAdded);
	}
	
	/**
	 * Reverses the order of the list between the start and stop index inclusively.
	 * 
	 * Assume the indices given are valid and start <= stop
	 * 
	 * @param start The beginning index of the sub section to be reversed
	 * @param stop The end index (inclusive) of the sub section to be reversed
	 */
	public void reverse(int start, int stop) {
		if (start == stop)
			return;
		Node<E> leftPointer = head, rightPointer = head, endNode = head; // previous the start node, previous the end node, and the ending node, respectively
		boolean b = false;
		if (start == 0) { // set leftPointer (previous the start node)
			for (int i = 0; i < size - 1; i++)
				leftPointer = leftPointer.getNext(); 
			b = true;
		}
		else
			for (int i = 0; i < start - 1; i++)
				leftPointer = leftPointer.getNext();
		
		for (int i = 0; i < stop - 1; i++) // set rightPointer (previous the end node)
			rightPointer = rightPointer.getNext();
		
		for (int i = 0; i < stop; i++) // set endNode
			endNode = endNode.getNext();
		
		
		if (start == 0 && stop == size - 1) { // if reversing the whole list
			head = endNode;
			reverse(start + 1, stop);
		}			
		else {
			rightPointer.setNext(endNode.getNext());
			endNode.setNext(leftPointer.getNext());
			if (b) // if reversing head to some point (not the end)
				head = endNode;
			leftPointer.setNext(endNode);
			reverse(start + 1, stop);
		}
	}
	
	/**
	 * This method will take in an index and move everything after 
	 * that index to the front of the list
	 * 
	 * Assume the index given is valid
	 * 
	 * @param index The index at which to cut the list
	 */
	public void flipFlop(int index) {
		
		Node<E> here = head;
		for (int i = 0; i < index; i++)
			here = here.getNext();
		head = here.getNext();
	}
	
	/**
	 * This method will reverse the order of the first half of the list up to 
	 * the index argument (inclusive), and also reverse the second half of the 
	 * list from index + 1 to the end of the list
	 * 
	 * Assume the index given is valid, however the second half may be empty
	 * 
	 * @param index The index to swing around
	 */
	public void swing(int index) throws IndexOutOfBoundsException {
		if (index < 0)
			throw new IndexOutOfBoundsException();
		reverse(0, index);
		if (index <= size - 2)
			reverse(index + 1, size - 1);
	}
}