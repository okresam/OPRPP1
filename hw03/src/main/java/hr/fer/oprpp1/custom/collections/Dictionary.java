package hr.fer.oprpp1.custom.collections;

/**
 * Object that stores data in key:value pairs.
 *
 * @param <K> - the type of the keys in the Dictionary
 * @param <V> - the type of the values in the Dictionary
 */
public class Dictionary<K, V> {
	
	/**
	 * ArrayIndexedCollection that stores all pairs in the Dictionary.
	 */
	private ArrayIndexedCollection<Pair> pairs = new ArrayIndexedCollection<>();
	
	/**
	 * Class that represents a key:value pair.
	 */
	private class Pair{
		
		/**
		 * The key of the pair.
		 */
		private K key;
		
		/**
		 * The value of the pair.
		 */
		private V value;
		
		/**
		 * Constructor for Dictionary. Sets the key and value to the given values.
		 * 
		 * @param key - wanted key of the pair
		 * @param value - wanted value of the pair
		 */
		public Pair(K key, V value) {
			if (key == null) {
				throw new NullPointerException();
			}
			
			this.key = key;
			this.value = value;
		}
	}
	
	/**
	 * Checks if the Dictionary is empty.
	 * 
	 * @return true if the Dictionary is empty, false otherwise
	 */
	public boolean isEmpty() {
		return pairs.isEmpty();
	}
	
	/**
	 * Returns the size of the Dictionary.
	 * 
	 * @return integer value, number of elements stored in the Dictionary
	 */
	int size() {
		return pairs.size();
	}
	
	/*
	 * Clears the whole Dictionary.
	 */
	public void clear() {
		pairs.clear();
	}
	
	/**
	 * Stores the given key:value pair into the Dictionary. If the given key already exists in the Dictionary,
	 * changes the value of the key to the given new value.
	 * 
	 * @param key - wanted key of the pair
	 * @param value - wanted value of the pair
	 * @return the old value of the key, null if the key does not exist in the Dictionary
	 */
	public V put(K key, V value) {
		ElementsGetter<Pair> eg = pairs.createElementsGetter();
		while(eg.hasNextElement()) {
			Pair p = eg.getNextElement();
			if (p.key.equals(key)) {
				V prevValue = p.value;
				p.value = value;
				return prevValue;
			}
		}
		
		pairs.add(new Pair(key, value));
		return null;
	}
	
	/**
	 * Finds the value for the given key in the Dictionary.
	 * 
	 * @param key - the key of which we want the value
	 * @return the value for the given key in the Dictionary
	 */
	public V get(Object key) {
		ElementsGetter<Pair> eg = pairs.createElementsGetter();
		while(eg.hasNextElement()) {
			Pair p = eg.getNextElement();
			if (p.key.equals(key)) {
				return p.value;
			}
		}
		
		return null;
	}
	
	/**
	 * Removes the pair for the given key from the Dictionary and returns the value of the pair.
	 * 
	 * @param key - key of the pair that we want to remove
	 * @return value that was removed from the Dictionary, null if the given key does not exist in the Dictionary
	 */
	public V remove(K key) {
		V value = get(key);
		
		ElementsGetter<Pair> eg = pairs.createElementsGetter();
		while(eg.hasNextElement()) {
			Pair p = eg.getNextElement();
			if (p.key.equals(key)) {
				pairs.remove(p);
				break;
			}
		}
			
		return value;
	}
}
