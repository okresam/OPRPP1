package hr.fer.oprpp1.custom.collections;

/**
 * Resizable array-backed collection of objects.
 */
public class ArrayIndexedCollection extends Collection{
	
	/**
	 * Number of stored elements in the ArrayIndexedCollection.
	 */
	private int size;
	
	/**
	 * Array of all elements stored in the ArrayIndexedCollection.
	 */
	private Object[] elements;
	
	/**
	 * Constructor that creates a new ArrayIndexedCollection and copies all elements from another
	 * given collection and sets the initial capacity to the given value. If the initial capacity 
	 * is smaller than the given other collection, the initial capacity is the size of the 
	 * given other collection.
	 * 
	 * @param other - collection whose elements are copied to this new collection
	 * @param initialCapacity - the initial capacity of the collection
	 * @throws NullPointerException - when the given other collection is a null reference
	 */
	public ArrayIndexedCollection(Collection other, int initialCapacity) {
		if (other == null) {
			throw new NullPointerException();
		}
		
		this.size = 0;
		
		if (other.size() > initialCapacity) {
			elements = new Object[other.size()];
		} else {
			elements = new Object[initialCapacity];
		}
		
		this.addAll(other);
	}
	
	/**
	 * Constructor that creates a new ArrayIndexedCollection and copies all elements from another
	 * given collection to this new one.
	 * 
	 * @param other - collection whose elements are copied to this new collection
	 * @throws NullPointerException - when the given other collection is a null reference
	 */
	public ArrayIndexedCollection(Collection other) {
		this(other, 0);
	}
	
	/**
	 * Constructor which sets the initial capacity of the collection to the given int value.
	 * @param initialCapacity - int value of the initial capacity
	 * @throws IllegalArgumentException - when the given initial capacity is less than one
	 */
	public ArrayIndexedCollection(int initialCapacity) {
		if (initialCapacity < 1) {
			throw new IllegalArgumentException();
		}
		
		this.size = 0;
		this.elements = new Object[initialCapacity];
	}
	
	/**
	 * Default constructor which sets the initial capacity of the collection to 16.
	 */
	public ArrayIndexedCollection() {
		this(16);
	}

	/**
	 * @throws NullPointerException - when the given value is a null reference
	 */
	public void add(Object value) {
		if (value == null) {
			throw new NullPointerException();
		}
		if (this.size == this.elements.length) {
			Object[] temp = new Object[this.elements.length * 2];
			
			for (int i = 0; i < this.elements.length; i++) {
				temp[i] = this.elements[i];
			}
			
			this.elements = temp;
		}
		this.elements[size++] = value;
	}
	
	/**
	 * Returns the object that is located on the given index in the collection.
	 * 
	 * @param index - int value of the index of the wanted object 
	 * @return object - that is located on the given index in the collection
	 * @throws IndexOutOfBoundsException - when the given index is less than zero or bigger than the size of 
	 * the collection minus one
	 */
	public Object get(int index) {
		if (index < 0 || index > this.size - 1) {
			throw new IndexOutOfBoundsException();
		}
		return this.elements[index];
	}
	
	public void clear() {
		for (int i = 0; i < this.elements.length; i++) {
			this.elements[i] = null;
		}
		
		this.size = 0;
	}
	
	/**
	 * Inserts given object on the given position.
	 * 
	 * @param value - object that we want to insert into the collection
	 * @param position - position at which the object will be inserted into the collection
	 * @throws IndexOutOfBoundsException - when the given index is less than zero or bigger than the size of 
	 * the collection minus one
	 * @throws NullPointerException - when the given value is a null reference
	 */
	public void insert(Object value, int position) {
		if (position < 0 || position > this.size) {
			throw new IndexOutOfBoundsException();
		}
		
		if (value == null) {
			throw new NullPointerException();
		}
		
		if (this.size == this.elements.length) {
			Object[] temp = new Object[this.elements.length * 2];
			
			for (int i = 0; i < this.elements.length; i++) {
				temp[i] = this.elements[i];
			}
			
			this.elements = temp;
		}
		
		int i = this.size;
		for (; i > position; i--) {
			this.elements[i] = this.elements[i-1];
		}
		
		this.size++;
		this.elements[i] = value;
		
	}
	
	/**
	 * Finds the index of the given object in the collection.
	 * 
	 * @param value - object of which we want the index in the collection
	 * @return int value of the index of the given object in the collection, returns -1 if the given
	 * object is not in the collection
	 */
	public int indexOf(Object value) {
		for (int i = 0; i < this.size; i++) {
			if (this.elements[i].equals(value)) {
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * Removes the object at the given index in the collection.
	 * 
	 * @param index - int value of the index of the position of the object which we want to remove
	 *  from the collection
	 * @throws IndexOutOfBoundsException - when the given index is less than zero or bigger than the size of 
	 * the collection minus one
	 */
	public void remove(int index) {
		if (index < 0 || index > this.size - 1) {
			throw new IndexOutOfBoundsException();
		}
		
		int i = index;
		for (; i < this.size - 1; i++) {
			this.elements[i] = this.elements[i+1];
		}
		this.elements[i] = null;
		
		this.size--;
	}
	
	public int size() {
		return this.size;
	}
	
	public boolean contains(Object value) {
		for (int i = 0; i < this.size; i++) {
			if (this.elements[i].equals(value)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean remove(Object value) {
		for (int i = 0; i < this.size; i++) {
			if (this.elements[i].equals(value)) {
				int j = i;
				for (; j < this.size - 1; j++) {
					this.elements[j] = this.elements[j+1];
				}
				this.elements[j] = null;
				
				this.size--;
				return true;
			}
		}
		return false;
	}
	
	public Object[] toArray() {
		Object[] arr = new Object[this.size];
		for (int i = 0; i < this.size; i++) {
			arr[i] = this.elements[i];
		}
		return arr;
	}
	
	public void forEach(Processor processor) {
		for (int i = 0; i < this.size; i++) {
			processor.process(this.elements[i]);
		}
	}
}
