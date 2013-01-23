import java.util.Collection;

/**
 * This is a circular, singly linked list.
 */
public class LinkedList<E> implements List<E> {

	protected Node<E> head = null;

	protected int size = 0;

	@Override
	public void add(E e) {
		Node<E> node = new Node<E>(e);
		
		if (head == null) {
			head = node;
			head.setNext(head);
			size++;
			return;
		}
		
		Node<E> here = head;
		while (here.getNext() != head) // check reference, not data
			here = here.getNext();
		here.setNext(node);
		node.setNext(head);
		size++;
	}

	/*
	 * You will want to look at Iterator, Iterable, and 
	 * how to use a for-each loop for this method.
	 */
	@Override
	public void addAll(Collection<? extends E> c) {
		for (E i : c) {
			add(i);
		}
		
	}

	@Override
	public void clear() {
		head = null;
		size = 0;
	}

	@Override
	public boolean contains(Object o) {
		
		if (indexOf(o) != -1)
			return true;
		return false;
	}

	@Override
	public E get(int index) throws IndexOutOfBoundsException {
		
		checkIndex(index);
		Node<E> here = head;
		for (int i = 0; i < index; i++)
			here = here.getNext();
		return here.getData();
	}

	@Override
	public int indexOf(Object o) {
		
		int index = 0;
		boolean found = false;
		Node<E> here = head;
		while (!here.getNext().equals(head)) {
			if ((o == null && here.getData() == null) || (o.equals(here.getData()))) {
				found = true;
				break;
			}
			here = here.getNext();
			index++;
		}
		if (found || ((o == null && here.getData() == null) || (o.equals(here.getData()))))
			return index;
		else 
			return -1;
	}

	@Override
	public boolean isEmpty() {
		return (head == null);
	}

	@Override
	public E remove(int index) throws IndexOutOfBoundsException {
		
		checkIndex(index);
		Node<E> here = head;
		for (int i = 0; i < index - 1; i++)
			here = here.getNext();
		Node<E> removal = here.getNext();
		here.setNext(here.getNext().getNext());
		size--;
		return removal.getData();
	}

	@Override
	public E remove(Object o) throws IndexOutOfBoundsException {
		if (indexOf(o) == -1)
			return null;
		return remove(indexOf(o));
	}

	@Override
	public E set(int index, E e) throws IndexOutOfBoundsException{
		
		checkIndex(index);
		Node<E> here = head;
		for (int i = 0; i < index; i++)
			here = here.getNext();
		E data = here.getData();
		here.setData(e);
		
		return data;
	}

	@Override
	public int size() {
		return size;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		
		String result = "[ ";
		Node<E> here = head;
		if (!isEmpty())
			for (int i = 0; i < size; i++) {
				result += here.getData() + " ";
				here = here.getNext();
			}
		result += "]";
		return result;
	}
	
	/**
	 * @param index
	 * @throws IndexOutOfBoundsException
	 */
	protected void checkIndex(int index) throws IndexOutOfBoundsException {
		if (index >= size || index < 0)
			throw new IndexOutOfBoundsException();
	}

	/*
	 * The following methods are for grading. Do not modify them, and you do not
	 * need to use them.
	 */

	public void setSize(int size) {
		this.size = size;
	}

	public Node<E> getHead() {
		return head;
	}

	public void setHead(Node<E> head) {
		this.head = head;
	}
}