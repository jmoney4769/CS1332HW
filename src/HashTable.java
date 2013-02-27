import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class HashTable<K, V> {

	/**
	 * The maximum load factor for this hashtable
	 */
	private final double MAX_LOAD_FACTOR = .64;

	/**
	 * The number of entries in this hashtable
	 */
	private int size;

	/**
	 * The underlying array for this hashtable
	 */
	private Entry<K, V>[] table;

	/**
	 * Constructor for HashTable
	 */
	public HashTable() {
		table = new Entry[11];
		size = 0;
	}

	/**
	 * Puts the key value pair into the table. If the key already exists in the
	 * table, replace the old value with the new one and return the old value
	 * 
	 * @param key
	 *            , never null
	 * @param value
	 *            , possibly null
	 * @return the replaced value, null if nothing existed previously
	 */
	public V put(K key, V value) {

		V returnValue = null;
		int hash = hash(key);
		if (table[hash] == null) // no element there
			table[hash] = new Entry<K, V>(key, value);
		else if (!table[hash].isAvailable() || table[hash].getKey().equals(key)) { // removed
																					// element
																					// there
			returnValue = table[hash].value;
			table[hash] = new Entry<K, V>(key, value);
		} else { // element in the way
			boolean inserted = false;
			while (!inserted) { // table cannot be full, so it will be inserted
				if (hash == table.length - 1)
					hash = -1; // so the increment will make it zero
				if (table[++hash] == null) {// after this point, hash has been
											// incremented
					table[hash] = new Entry<K, V>(key, value);
					inserted = true;
				} else if (!table[hash].isAvailable()) {
					returnValue = table[hash].getValue();
					table[hash] = new Entry<K, V>(key, value);
					inserted = true;
				}
			}
		}
		size++;
		checkLoadFactor();
		return returnValue;
	}

	/**
	 * Removes the entry containing the given key
	 * 
	 * (remember that all objects have a hashCode method)
	 * 
	 * @param key
	 *            , never null
	 * @return the value of the removed entry
	 */
	public V remove(Object key) {

		boolean found = true;
		int hash = hash(key);
		if (table[hash] == null)
			return null;
		else if (table[hash].getKey().equals(key) && table[hash].isAvailable()) 
			// object is at the hash that it should be
			table[hash].setAvailable(false);
		else {
			int iterations = 0;
			while (true) {
				if (hash == table.length - 1)
					hash = -1;
				if (table[++hash] == null)
					return null;
				else if (table[hash].getKey().equals(key)
						&& table[hash].isAvailable()) {
					table[hash].setAvailable(false);
					break;
				}
				iterations++;
				if (iterations == size) {
					found = false;
					break; // one of these break statements will be called
				}
			}
		}
		return found ? table[hash].getValue() : null;
	}

	/**
	 * Gets the value of the entry given a specific key
	 * 
	 * (remember that all objects have a hashCode method)
	 * 
	 * @param key
	 *            , never null
	 * @return
	 */
	public V get(Object key) {

		Entry<K, V> entry = entryAt(key);
		return (entry == null) ? null : entry.getValue();
	}

	/**
	 * Hashes the key of an element
	 * 
	 * @param key
	 *            The key of the element
	 * @return The hash value of the key
	 */
	private int hash(Object key) {
		return Math.abs(key.hashCode()) % table.length;
	}

	private void checkLoadFactor() {

		if ((double) size / table.length >= MAX_LOAD_FACTOR)
			resize();
	}

	/**
	 * Resize the table by making a new table with a size equal to the next
	 * prime after twice the current size
	 * 
	 */
	private void resize() {

		Entry<K, V> newTable[] = new Entry[table.length];
		for (int i = 0; i < table.length; i++)
			newTable[i] = table[i];
		//table = new Entry[nextPrime(table.length)];
		table = new Entry[table.length * 2 + 1];
		size = 0;
		for (int i = 0; i < newTable.length; i++)
			if (newTable[i] != null)
				put(newTable[i].getKey(), newTable[i].getValue());
	}

	/**
	 * @param size
	 * @return
	 */
	private int nextPrime(int newSize) {

		newSize *= 2; // double it
		while (true) {
			newSize++; // it cannot be double the current size
			int count = 0;
			for (int i = 1; i <= Math.sqrt(newSize); i++)
				if (newSize % i == 0)
					count++;
			if (count == 1)
				break;
		}
		return newSize;
	}

	/**
	 * @param key
	 *            , never null
	 * @return true if this table contains the given key, false otherwise
	 */
	public boolean containsKey(Object key) {
		return entryAt(key) != null;
	}

	public Entry<K, V> entryAt(Object key) {

		int hash = hash(key);
		if (table[hash] == null)
			return null;
		else if (table[hash].getKey().equals(key) && table[hash].isAvailable())
			// object is at the hash that it should be
			return table[hash];
		else {
			int iterations = 0;
			while (true) {
				if (hash == table.length - 1)
					hash = -1;
				if (table[++hash] == null)
					return null;
				else if (table[hash].getKey().equals(key)
						&& table[hash].isAvailable()) {
					return table[hash];
				}
				iterations++;
				if (iterations == size) {
					return null; // one of these break statements will be called
				}
			}
		}
	}

	/**
	 * Clears this hashTable
	 */
	public void clear() {
		for (int i = 0; i < table.length; i++)
			table[i] = null;
		size = 0;
	}

	/**
	 * @return true if this hashtable is empty, false otherwise
	 */
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * @return the value from this hashtable
	 */
	public Collection<V> values() {
		Collection<V> collection = new ArrayList<V>();
		for (Entry<K, V> i : table)
			if (i != null && i.isAvailable())
				collection.add(i.getValue());
		return collection;
	}

	/**
	 * @return the unique keys from this hashtable
	 */
	public Set<K> keySet() {
		HashSet<K> set = new HashSet<K>();
		for (Entry<K, V> i : table)
			if (i != null && i.isAvailable())
				set.add(i.getKey());
		return set;
	}

	/**
	 * @return the unique entries from this hashtable
	 */
	public Set<Entry<K, V>> entrySet() {
		HashSet<Entry<K, V>> set = new HashSet<Entry<K, V>>();
		for (Entry<K, V> i : table)
			if (i != null && i.isAvailable())
				set.add(i);
		return set;
	}

	/**
	 * @return the size of this hashtable
	 */
	public int size() {
		return size;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return Arrays.toString(table);
	}

	/*
	 * Don't modify any code below this point
	 */

	public void setSize(int size) {
		this.size = size;
	}

	public Entry<K, V>[] getTable() {
		return table;
	}

	public void setTable(Entry<K, V>[] table) {
		this.table = table;
	}

	public double getMaxLoadFactor() {
		return MAX_LOAD_FACTOR;
	}

	public static class Entry<K, V> {
		private K key;
		private V value;
		private boolean available;

		public Entry(K key, V value) {
			this.setKey(key);
			this.setValue(value);
			this.setAvailable(true);
		}

		public void setKey(K key) {
			this.key = key;
		}

		public K getKey() {
			return this.key;
		}

		public void setValue(V value) {
			this.value = value;
		}

		public V getValue() {
			return this.value;
		}

		public boolean isAvailable() {
			return available;
		}

		public void setAvailable(boolean available) {
			this.available = available;
		}

		/*
		 * toString method made for testing purposes
		 */
		@Override
		public String toString() {
			if (available)
				return "" + value;
			else
				return "hidden";
		}
	}
}