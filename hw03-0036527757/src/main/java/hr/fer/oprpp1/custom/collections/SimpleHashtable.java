package hr.fer.oprpp1.custom.collections;

import java.lang.reflect.Array;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A simple implementation of a Hashtable that stores data as key:pair pairs. 
 *
 * @param <K> - the type of the keys in the SimpleHashtable
 * @param <V> - the type of the values in the SimpleHashtable
 */
public class SimpleHashtable<K, V> implements Iterable<SimpleHashtable.TableEntry<K, V>>{
	
	/**
	 * Internal array for storing entries for the SimpleHashtable.
	 */
	private TableEntry<K, V>[] table;
	
	/**
	 * Number of stored elements in the SimpleHashtable.
	 */
	private int size;
	
	/**
	 * Keeps count of how many times a structural modification has been made 
	 * to the SimpleHashtable.
	 */
	private int modificationCount;
	
	/**
	 * Class that represents a entry in the SimpleHashtable.
	 * 
	 * @param <K> - the type of the key of the entry
	 * @param <V> - the type of the value of the entry
	 */
	public static class TableEntry<K, V> {
		
		/**
		 * The key of the entry.
		 */
		private K key;
		
		/**
		 * The value of the entry.
		 */
		private V value;
		
		/**
		 * Reference to the next entry.
		 */
		private TableEntry<K, V> next;
		
		/**
		 * Constructor for the TableEntry. Sets the key and value to the given values.
		 * 
		 * @param key - wanted key of the entry
		 * @param value - wanted value of the entry
		 */
		public TableEntry(K key, V value) {
			this.key = key;
			this.value = value;
		}
		
		/**
		 * Getter for the key.
		 * 
		 * @return the key of the entry
		 */
		public K getKey() {
			return this.key;
		}
		
		/**
		 * Getter for the value.
		 * 
		 * @return the value of the entry
		 */
		public V getValue() {
			return this.value;
		}
		
		/**
		 * Setter for the value.
		 * 
		 * @param value - wanted value of the entry
		 */
		public void setValue(V value) {
			this.value = value;
		}
	}
	
	/**
	 * Default constructor for the SimpleHashtable. Sets the initial capacity of the SimpleHashtable to 16.
	 */
	public SimpleHashtable() {
		this(16);
	}
	
	/**
	 * Constructor for the SimpleHashtable. Sets the inital capacity of the SimpleHashtable to the given value.
	 * 
	 * @param initialCapacity - wanted initial capacity 
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashtable(int initialCapacity) {
		if (initialCapacity < 1) {
			throw new IllegalArgumentException();
		}
		
		int s = 2;
		while (s < initialCapacity) {
			s *= 2;
		}
		
		this.size = 0;
		this.modificationCount = 0;
		this.table = new TableEntry[s];
	}
	
	/**
	 * Stores the given key:value entry into the SimpleHashtable. If the given key already exists in the SimpleHashtable,
	 * changes the value of the key to the given new value.
	 * 
	 * @param key - wanted key of the entry
	 * @param value - wanted value of the entry
	 * @return the old value of the key, null if the key does not exist in the SimpleHashtable
	 */
	@SuppressWarnings("unchecked")
	public V put(K key, V value) {
		if (key == null) {
			throw new NullPointerException();
		}
		
		if ((double) this.size / this.table.length >= 0.75) {
			TableEntry<K, V>[] temp = this.toArray();
			this.table = (TableEntry<K, V>[]) Array.newInstance(table.getClass().getComponentType(), size()*2);
			this.modificationCount -= (this.size() - 1);
			this.size = 0;
			for (int i = 0; i < temp.length; i++) {
				this.put(temp[i].getKey(), temp[i].getValue());
			}
		}
		
		int slot = Math.abs(key.hashCode() % this.table.length);
		
		if (table[slot] == null) {
			table[slot] = new TableEntry<>(key, value);
			this.size++;
			this.modificationCount++;
			return null;
		}
		
		TableEntry<K, V> n = table[slot];
		TableEntry<K, V> prev = table[slot];
		while(n != null) {
			if (n.getKey().equals(key)) {
				V oldV = n.getValue();
				n.setValue(value);
				return oldV;
			}
			
			prev = n;
			n = n.next;
		}
		
		prev.next = new TableEntry<>(key, value);
		this.size++;
		this.modificationCount++;
		return null;
	}
	
	/**
	 * Finds the value for the given key in the SimpleHashtable.
	 * 
	 * @param key - the key of which we want the value
	 * @return the value for the given key in the SimpleHashtable
	 */
	public V get(Object key) {
		if (containsKey(key)) {
			int slot = Math.abs(key.hashCode() % this.table.length);
			TableEntry<K, V> n = table[slot];
			while(n != null) {
				if (n.getKey().equals(key)) {
					return n.getValue();
				}
				
				n = n.next;
			}
		}
		return null;
	}
	
	/**
	 * Returns the number of currently stored elements in the SimpleHashtable.
	 * 
	 * @return integer value size of the SimpleHashtable
	 */
	public int size() {
		return this.size;
	}
	
	/**
	 * Checks if the SimpleHashtable contains the given key.
	 * 
	 * @param key - the key that is checked if it is inside the SimpleHashtable
	 * @return true if the key exist in the SimpleHashtable, false otherwise
	 */
	public boolean containsKey(Object key) {
		if (key == null) {
			return false;
		}
		
		int slot = Math.abs(key.hashCode() % this.table.length);
		TableEntry<K, V> n = table[slot];
		while(n != null) {
			if (n.getKey().equals(key)) {
				return true;
			}
			
			n = n.next;
		}
		
		return false;
	}
	
	/**
	 * Checks if the SimpleHashtable contains the given value.
	 * 
	 * @param value - the value that is checked if it is inside the SimpleHashtable
	 * @return true if the value exist in the SimpleHashtable, false otherwise
	 */
	public boolean containsValue(Object value) {
		for (int i = 0; i < this.table.length; i++) {
			TableEntry<K, V> n = table[i];
			while(n != null) {
				if (n.getValue().equals(value)) {
					return true;
				}
				
				n = n.next;
			}
		}
		return false;
	}
	
	/**
	 * Removes the entry for the given key from the SimpleHashtable and returns the value of the entry.
	 * 
	 * @param key - key of the entry that we want to remove
	 * @return value of the entry that was removed from the SimpleHashtable, 
	 * null if the given key does not exist in the SimpleHashtable
	 */
	public V remove(Object key) {
		if (containsKey(key)) {
			int slot = Math.abs(key.hashCode() % this.table.length);
			
			if (table[slot].getKey().equals(key)) {
				V oldV = table[slot].getValue();
				table[slot] = table[slot].next;
				this.size--;
				this.modificationCount++;
				return oldV;
			}
			
			TableEntry<K, V> n = table[slot];
			TableEntry<K, V> prev = table[slot];
			
			while(!n.getKey().equals(key)) {
				if (n.getKey().equals(key)) {
					V oldV = n.getValue();
					prev.next = n.next;
					this.size--;
					this.modificationCount++;
					return oldV;
				}
				
				prev = n;
				n = n.next;
			}
		}
		return null;
	}
	
	/**
	 * Checks if the SimpleHashtable is empty.
	 * 
	 * @return true if the SimpleHashtable is empty, false otherwise
	 */
	public boolean isEmpty() {
		return this.size == 0;
	}
	
	/**
	 * Returns a string representation of the SimpleHashtable. 
	 * 
	 * @return string representation of the SimpleHashtable (all of the elements)
	 */
	public String toString() {
		String output = "[";
		int outputElements = 0;
		for (int i = 0; i < this.table.length; i++) {
			TableEntry<K, V> n = table[i];
			while(n != null) {
				output += String.valueOf(n.getKey()) + "=" + String.valueOf(n.getValue());
				outputElements++;
				
				if (outputElements < this.size()) {
					output += ", ";
				}
				
				n = n.next;
			}
		}
		
		return output + "]";
	}
	
	/**
	 * Creates and returns an array of all the elements in the SimpleHashtable.
	 * 
	 * @return array of all elements that are currently in the SimpleHashtable
	 */
	public TableEntry<K, V>[] toArray() {
		@SuppressWarnings("unchecked")
		TableEntry<K, V>[] arr = (TableEntry<K, V>[]) Array.newInstance(table.getClass().getComponentType(), size());
		int index = 0;
		for (int i = 0; i < this.table.length; i++) {
			TableEntry<K, V> n = table[i];
			while(n != null) {
				arr[index++] = n;
				
				n = n.next;
			}
		}
		return arr;
	}
	
	/**
	 * Clears the whole SimpleHashtable.
	 */
	public void clear() {
		this.size = 0;
		for (int i = 0; i < this.table.length; i++) {
			table[i] = null;
		}
		this.modificationCount++;
	}

	/**
	 * Iterator implementation for the SimpleHashtable.
	 */
	private class IteratorImpl implements Iterator<SimpleHashtable.TableEntry<K, V>> {

		/**
		 * Number of modifications when the Iterator was initialized.
		 */
		private int savedModificationCount;
		
		/**
		 * Index for the internal array table of the SimpleHashtable.
		 */
		private int tableIndex;
		
		/**
		 * Number of elements stored in the SimpleHashtable.
		 */
		private int numberOfElements;
		
		/**
		 * Size of the internal array table of the SimpleHashtable when the iterator was created.
		 */
		private int tableSize;
		
		/**
		 * Next element to be returned.
		 */
		private TableEntry<K, V> nextElement;
		
		/**
		 * The element that was returned on the last call of the method next().
		 */
		private TableEntry<K, V> latestElement;
		
		/**
		 * Default constructor for the IteratorImpl.
		 */
		public IteratorImpl() {
			this.savedModificationCount = modificationCount;
			this.tableIndex = 0;
			this.numberOfElements = 0;
			this.tableSize = size();
		}
		
		@Override
		public boolean hasNext() {
			if (savedModificationCount != modificationCount) {
				throw new ConcurrentModificationException();
			}
			
			return this.numberOfElements < this.tableSize;
		}

		@Override
		public SimpleHashtable.TableEntry<K, V> next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			
			TableEntry<K, V> element = nextElement;
			
			while (element == null) {
				element = table[tableIndex++];
			}
			
			latestElement = element;
			nextElement = element.next;
			numberOfElements++;
			return element;
		}
		
		@Override
		public void remove() {
			hasNext();
			if (latestElement == null) {
				throw new IllegalStateException();
			}
			
			SimpleHashtable.this.remove(latestElement.getKey());
			latestElement = null;
			savedModificationCount++;
		}
	}
	
	/**
	 * Returns an iterator over elements of the SimpleHashtable.
	 */
	@Override
	public Iterator<TableEntry<K, V>> iterator() {
		return new IteratorImpl();
	}
}
 