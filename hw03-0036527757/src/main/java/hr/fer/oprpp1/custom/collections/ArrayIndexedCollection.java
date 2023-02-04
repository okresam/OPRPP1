package hr.fer.oprpp1.custom.collections;

import java.lang.reflect.Array;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

/**
 * Resizable array-backed collection.
 * 
 * @param <T> - the type of the elements in the ArrayIndexedCollection
 */
public class ArrayIndexedCollection<T> implements List<T>{
	
	/**
	 * Number of elements currently stored in the ArrayIndexedCollection.
	 */
	private int size;
	/**
	 * Array of all currently stored elements in the ArrayIndexedCollection.
	 */
	private T[] elements;
	
	/**
	 * Keeps count of how many times a structural modification has been made 
	 * to the ArrayIndexedCollection.
	 */
	private long modificationCount = 0;
	
	/**
	 * Constructor that creates a new ArrayIndexedCollection and copies all elements from another
	 * given collection and sets the initial capacity to the given value. If the initial capacity 
	 * is smaller than the given other collection, the initial capacity is the size of the 
	 * given other collection.
	 * 
	 * @param other - collection whose elements are copied to this new collection
	 * @param initialCapacity - the initial capacity of the collection
	 * @throws NullPointerException when a null reference is given for the other collection
	 */
	@SuppressWarnings("unchecked")
	public ArrayIndexedCollection(Collection<? extends T> other, int initialCapacity) {
		if (other == null) {
			throw new NullPointerException();
		}
		
		this.size = 0;
		
		if (other.size() > initialCapacity) {
			elements = (T[]) new Object[other.size()];
		} else {
			elements = (T[]) new Object[initialCapacity];
		}
		
		this.addAll(other);
	}
	
	/**
	 * Constructor that creates a new ArrayIndexedCollection and copies all elements from another
	 * given collection to this new one.
	 * 
	 * @param other - collection whose elements are copied to this new collection
	 */
	public ArrayIndexedCollection(Collection<? extends T> other) {
		this(other, 0);
	}
	
	/**
	 * Constructor which sets the initial capacity of the collection to the given integer value.
	 * @param initialCapacity - integer value of the initial capacity
	 * @throws IllegalArgumentException when the given initialCapacity is smaller than 1
	 */
	@SuppressWarnings("unchecked")
	public ArrayIndexedCollection(int initialCapacity) {
		if (initialCapacity < 1) {
			throw new IllegalArgumentException();
		}
		
		this.size = 0;
		this.elements = (T[]) new Object[initialCapacity];
	}
	
	/**
	 * Default constructor which sets the initial capacity of the collection to 16.
	 */
	public ArrayIndexedCollection() {
		this(16);
	}

	/**
	 * Adds the given argument to the ArrayindexedCollection at the end.
	 * 
	 * @throws NullPointerException when a null reference is given
	 */
	@Override
	public void add(T value) {
		if (value == null) {
			throw new NullPointerException();
		}
		if (this.size == this.elements.length) {
			@SuppressWarnings("unchecked")
			T[] temp = (T[]) new Object[this.elements.length * 2];
			
			for (int i = 0; i < this.elements.length; i++) {
				temp[i] = this.elements[i];
			}
			
			this.elements = temp;
		}
		this.elements[size++] = value;
		
		modificationCount++;
	}
	
	/**
	 * Returns the element that is located on the given index in the collection.
	 * 
	 * @param index - integer value of the index of the wanted object 
	 * @return element that is located on the given index in the ArrayIndexedCollection
	 * @throws IndexOutOfBoundsException when the given index is smaller than 0 or bigger than 
	 * the size of the ArrayIndexedCollection - 1
	 */
	public T get(int index) {
		if (index < 0 || index > this.size - 1) {
			throw new IndexOutOfBoundsException();
		}
		return this.elements[index];
	}
	
	/**
	 * Clears the whole ArrayIndexedCollection.
	 */
	@Override
	public void clear() {
		for (int i = 0; i < this.elements.length; i++) {
			this.elements[i] = null;
		}
		
		this.size = 0;
		
		modificationCount++;
	}
	
	/**
	 * Inserts the given argument on the given position in the ArrayIndexedCollection.
	 * 
	 * @param value - element that we want to insert into the ArrayIndexedCollection
	 * @param position - position at which the element will be inserted into the ArrayIndexedCollection
	 * @throws IndexOutOfBoundsException when the given position is smaller than 0 or bigger 
	 * than the size of the ArrayIndexedCollection - 1
	 * @throws NullPointerException when the given argument is a null reference
	 */
	public void insert(T value, int position) {
		if (position < 0 || position > this.size) {
			throw new IndexOutOfBoundsException();
		}
		
		if (value == null) {
			throw new NullPointerException();
		}
		
		if (this.size == this.elements.length) {
			@SuppressWarnings("unchecked")
			T[] temp = (T[]) new Object[this.elements.length * 2];
			
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
	
		modificationCount++;
	}
	
	/**
	 * Finds the index of the given object in the ArrayIndexedCollection.
	 * 
	 * @param value - object of which we want the index in the ArrayIndexedCollection
	 * @return integer value of the index of the given object in the ArrayIndexedCollection, 
	 * returns -1 if the given element is not in the collection
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
	 * Removes the element at the given index in the ArrayIndexedCollection.
	 * 
	 * @param index - integer value of the index of the element which we want to remove from 
	 * the ArrayIndexedCollection
	 *  @throws IndexOutOfBoundsException when the index is smaller than 0 or bigger than the 
	 *  size of the ArrayIndexedCollection - 1
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
		modificationCount++;
	}
	
	/**
	 * Returns integer value of the size of the ArrayIndexedCollection. Number of currently stored elements in the
	 * ArrayIndexedCollection.
	 */
	@Override
	public int size() {
		return this.size;
	}
	
	/**
	 * Checks if the given object is inside the ArrayIndexedCollection.
	 */
	@Override
	public boolean contains(Object value) {
		for (int i = 0; i < this.size; i++) {
			if (this.elements[i].equals(value)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Checks if the given object is inside the ArrayIndexedCollection and removes it.
	 * (If it is found.)
	 */
	@Override
	public boolean remove(Object value) {
		for (int i = 0; i < this.size; i++) {
			if (this.elements[i].equals(value)) {
				int j = i;
				for (; j < this.size - 1; j++) {
					this.elements[j] = this.elements[j+1];
				}
				this.elements[j] = null;
				
				this.size--;
				modificationCount++;
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Creates an object array of all the elements inside the ArrayIndexedCollection and returns it.
	 */
	@Override
	public Object[] toArray() {
		Object[] arr = new Object[this.size];
		for (int i = 0; i < this.size; i++) {
			arr[i] = this.elements[i];
		}
		return arr;
	}
	
	/**
	 * Creates an array of all the elements inside the ArrayIndexedCollection.
	 * If the given array does not have enough space, a new bigger array is created.
	 */
	@Override
	public T[] toArray(T[] arr) {
		if (arr.length >= this.size()) {
			System.arraycopy(elements, 0, arr, 0, size());
			return arr;
		}
		
		@SuppressWarnings("unchecked")
		T[] nArr = (T[])Array.newInstance(arr.getClass().getComponentType(), size());
		System.arraycopy(elements, 0, nArr, 0, size());
		return nArr;
	}
	
	/**
	 *  Class that implements an ElementsGetter for the ArrayIndexedCollection.
	 */
	private static class ElementsGetterAIC<G> implements ElementsGetter<G>{
	
		/**
		 * Index of the current next element of the ElementsGetter.
		 */
		private int egIndex = 0;
		
		/**
		 * Reference to the ArrayIndexedCollection instance.
		 */
		ArrayIndexedCollection<G> aicRef;
		
		/**
		 * Number of saved modifications when the ElementsGetter was initialized.
		 */
		private long savedModificationCount;
		
		/**
		 * Constructor for the ElementsGetterAIC. Sets the ArrayIndexedCollection reference of the class
		 * to the given reference.
		 * 
		 * @param col reference to an ArrayIndexedCollection instance
		 */
		public ElementsGetterAIC(ArrayIndexedCollection<G> col) {
			this.aicRef = col;
			this.savedModificationCount = col.modificationCount;
		}
		
		/**
		 * Checks if the ArrayIndexedCollection has a next element.
		 * 
		 * @return boolean value true if the ArrayIndexedCollection has a next element, false if it does not
		 * @throws ConcurrentModificationException if the ArrayIndexedCollection was structurally modified 
		 * since the ElemetsGetter was initialized
		 */
		@Override
		public boolean hasNextElement() {
			if (this.savedModificationCount != aicRef.modificationCount) {
				throw new ConcurrentModificationException();
			}
			
			if (egIndex < aicRef.size) {
				return true;
			}
			return false;
		}
		
		/**
		 * Returns the next element of the ArrayIndexedCollection for this ElementGetter.
		 * 
		 * @return next element of the ArrayIndexedCollection
		 * @throws NoSuchElementException when this method is called and the ArrayIndexedCollectin does not have 
		 * a next element
		 * @throws ConcurrentModificationException if the ArrayIndexedCollection was structurally modified 
		 * since the ElemetsGetter was initialized
		 */
		@Override
		public G getNextElement() {
			if (this.savedModificationCount != aicRef.modificationCount) {
				throw new ConcurrentModificationException();
			}
			
			if (egIndex >= aicRef.size) {
				throw new NoSuchElementException();
			}
			return aicRef.elements[egIndex++];
		}
	}
	
	/**
	 * Creates a new ElementsGetter instance for an ArrayIndexedCollection instance.
	 * 
	 * @returns reference to new ElementsGetter instance
	 */
	@Override
	public ElementsGetter<T> createElementsGetter() {
		return new ElementsGetterAIC<T>(this);
	}
}
