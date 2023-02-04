package hr.fer.oprpp1.custom.collections;

import java.lang.reflect.Array;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

/**
 * Linked list-backed collection.
 */
public class LinkedListIndexedCollection<T> implements List<T>{
	
	/**
	 * A node of the list which has a reference to the previous and the next node and a value.
	 */
	private static class ListNode<G> {
		ListNode<G> previous;
		ListNode<G> next;
		G value;
	}
	
	/**
	 * Number of elements currently stored in the LinkedListIndexedCollection.
	 */
	private int size;
	
	/**
	 * First ListNode in the LinkedListIndexedCollcetion.
	 */
	private ListNode<T> first;
	
	/**
	 * Last ListNode in the LinkedListIndexedCollection.
	 */
	private ListNode<T> last;
	
	/**
	 * Keeps count of how many times a structural modification has been made 
	 * to the LinkedListIndexedCollection.
	 */
	private long modificationCount = 0;
	
	/**
	 * Default constructor that sets the size of the collection to 0 and first and last node to null.
	 */
	public LinkedListIndexedCollection() {
		this.size = 0;
		this.first = null;
		this.last = null;
	}
	
	/**
	 * Constructor creates a new LinkedListIndexedCollection and copies all elements from the given
	 * other collection to this new collection.
	 * 
	 * @param other - collection from which we want elements to be copied to this new collection
	 */
	public LinkedListIndexedCollection(Collection<? extends T> other) {
		this.size = 0;
		this.addAll(other);
	}
	
	/**
	 * Adds the given argument to the end of the LinkedListIndexedCollection.
	 * 
	 * @throws NullPointerException when the given element is a null reference
	 */
	@Override
	public void add(T value) {
		if (value == null) {
			throw new NullPointerException();
		}
		
		if (this.first == null && this.last == null) {
			ListNode<T> n = new ListNode<>();
			n.value = value;
			
			this.first = n;
			this.last = n;
		} else {
			ListNode<T> n = new ListNode<>();
			n.value = value;
			
			n.previous = this.last;
			this.last.next = n;
			this.last = n;
		}
		
		this.size++;
		modificationCount++;
	}
	
	/**
	 * Returns the element that is located on the given index in the LinkedListIndexedCollection.
	 * 
	 * @param index - integer value of the index of the wanted element
	 * @return element that is located on the given index
	 * @throws IndexOutOfBoundsException when the given index is smaller than 0 or bigger
	 * than the size of the LinkedListIndexedCollection - 1
	 */
	public T get(int index) {
		if (index < 0 || index > this.size - 1) {
			throw new IndexOutOfBoundsException();
		}
		
		ListNode<T> n;
		
		if (index < (this.size - 1) - index) {
			n = this.first;
			
			for (int i = 0; i < index; i++) {
				n = n.next;
			}
		} else {
			n = this.last;
			
			for (int i = 0; i < (this.size - 1) - index; i++) {
				n = n.previous;
			}
		}
		
		return n.value;
	}
	
	/**
	 * Clears the whole LinkedListIndexedCollection.
	 */
	@Override
	public void clear() {
		while(this.first.next != null) {
			ListNode<T> n = this.first.next;
			this.first.next = null;
			n.previous = null;
			this.first = n;
		}
		
		this.size = 0;
		
		modificationCount++;
	}
	
	/**
	 * Inserts given argument on the given position in the LinkedListIndexedCollection.
	 * 
	 * @param value - element that we want to insert
	 * @param position - position at which the element will be inserted
	 * @throws IndexOutOfBoundsException when the given position is smaller than 0 or bigger
	 * than the size of the LinkedListIndexedCollection - 1
	 * @throws NullPointerException when the given element is a null reference
	 */
	public void insert(T value, int position) {
		if (position < 0 || position > this.size) {
			throw new IndexOutOfBoundsException();
		}
		
		if (value == null ) {
			throw new NullPointerException();
		}
		
		if (position == this.size) {
			ListNode<T> newNode = new ListNode<>();
			newNode.value = value;
			
			this.last.next = newNode;
			newNode.previous = this.last;
			
			this.last = newNode;
		} else if(position == 0) {
			ListNode<T> newNode = new ListNode<>();
			newNode.value = value;
			
			this.first.previous = newNode;
			newNode.next = this.first;
			
			this.first = newNode;
		} else {
			ListNode<T> positionNode = this.first;
			for (int i = 0; i < position; i++) {
				positionNode = positionNode.next;
			}
			ListNode<T> previousNode = positionNode.previous;
			ListNode<T> newNode = new ListNode<>();
			newNode.value = value;
		
			previousNode.next = newNode;
			newNode.previous = previousNode;
			newNode.next = positionNode;
			positionNode.previous = newNode; 
		}
		
		this.size++;
		
		modificationCount++;
	}
	
	/**
	 * Finds the index of the given object in the LinkedListIndexedCollection or returns -1 if the 
	 * wanted object is not found.
	 * 
	 * @param value - object of which we want the index
	 * @return integer value of the index of the given object. Returns -1 if the given
	 * object is not in the collection
	 */
	public int indexOf(Object value) {
		ListNode<T> n = this.first;
		int i = 0;
		while (n != null) {
			if (n.value.equals(value)) {
				return i;
			}
			n = n.next;
			i++;
		}
		return -1;
	}
	
	/**
	 * Removes the element at the given index in the LinkedListIndexedCollection.
	 * 
	 * @param index - integer value of the index of the object which we want to remove
	 *  @throws IndexOutOfBoundsException if the given index is smaller than 0 or bigger than
	 *  the size of the LinkedListIndexedCollection - 1
	 */
	public void remove(int index) {
		if (index < 0 || index > this.size - 1) {
			throw new IndexOutOfBoundsException();
		}
		
		if (index == this.size - 1) {
			ListNode<T> nodeBeforeLast = this.last.previous;
			nodeBeforeLast.next = null;
			this.last.previous = null;
			this.last = nodeBeforeLast;
		}else if (index == 0) {
			ListNode<T> nodeAfterFirst = this.first.next;
			nodeAfterFirst.previous = null;
			this.first.next = null;
			this.first = nodeAfterFirst;
		} else {
			ListNode<T> indexNode = this.first;
			for (int i = 0; i < index; i++) {
				indexNode = indexNode.next;
			}
			ListNode<T> previousNode = indexNode.previous;
			ListNode<T> nextNode = indexNode.next;
		
			previousNode.next = nextNode;
			nextNode.previous = previousNode;
			
			indexNode.next = null;
			indexNode.previous = null;
		}
		
		this.size--;
		
		modificationCount++;
	}
	
	/**
	 * Returns the size of the LinkedListIndexedCollection. Number of currently stored elements in 
	 * the LinkedListIndexedCollection.
	 */
	@Override
	public int size() {
		return this.size;
	}
	
	/**
	 * Checks if the given object is inside the LinkedListIndexedCollection.
	 */
	@Override
	public boolean contains(Object value) {
		ListNode<T> n = this.first;
		while(n != null) {
			if (n.value.equals(value)) {
				return true;
			}
			n = n.next;
		}
		return false;
	}
	
	/**
	 * Checks if the given object is inside the LinkedListIndexedCollection and removes it.
	 * (If it is found.)
	 */
	@Override
	public boolean remove(Object value) {
		ListNode<T> n = this.first;
		while(n != null) {
			if (n.value.equals(value)) {
				if (n == this.last) {
					ListNode<T> nodeBeforeLast = this.last.previous;
					nodeBeforeLast.next = null;
					this.last.previous = null;
					this.last = nodeBeforeLast;
				}else if (n == this.first) {
					ListNode<T> nodeAfterFirst = this.first.next;
					nodeAfterFirst.previous = null;
					this.first.next = null;
					this.first = nodeAfterFirst;
				} else {
					ListNode<T> previousNode = n.previous;
					ListNode<T> nextNode = n.next;
				
					previousNode.next = nextNode;
					nextNode.previous = previousNode;
					
					n.next = null;
					n.previous = null;
				}
				
				this.size--;
				modificationCount++;
				return true;
			}
			n = n.next;
		}
		return false;
	}
	
	/**
	 * Creates an array of all the objects inside the LinkedListIndexedCollection and returns it.
	 */
	@Override
	public Object[] toArray() {
		Object[] arr = new Object[this.size];
		ListNode<T> n = this.first;
		for (int i = 0; i < this.size; i++) {
			arr[i] = n.value;
			n = n.next;
		}
		return arr;
	}
	
	/**
	 *  Creates an array of all elements in the LinkedListIndexedCollection. 
	 *  If the given array does not have enough space, a new bigger array is created.
	 */
	@Override
	public T[] toArray(T[] arr) {
		ListNode<T> n = this.first;
		
		if (arr.length >= this.size()) {
			
			for (int i = 0; i < this.size; i++) {
				arr[i] = n.value;
				n = n.next;
			}
			return arr;
		}
		
		@SuppressWarnings("unchecked")
		T[] nArr = (T[])Array.newInstance(arr.getClass().getComponentType(), size());
		for (int i = 0; i < this.size; i++) {
			nArr[i] = n.value;
			n = n.next;
		}
		return nArr;
	}
	
	/**
	 *  Class that implements an ElementsGetter for the LinkedListIndexedCollection.
	 */
	private static class ElementsGetterLLIC<G> implements ElementsGetter<G>{
		
		/**
		 * Reference to a ListNode of the LinkedListIndexedCollection.
		 */
		private ListNode<G> nextNode;
		
		/**
		 * Reference to the LinkedListIndexedCollection instance.
		 */
		LinkedListIndexedCollection<G> llicRef;
		
		/**
		 * Number of saved modifications when the ElementsGetter was initialized.
		 */
		private long savedModificationCount;
		
		/**
		 * Constructor for the ElementsGetterLLIC. Sets the LinkedListIndexedCollection reference of the class 
		 * to the given reference and sets the reference to the first node in the given LinkedListIndexedCollection.
		 * 
		 * @param col - reference to an LinkedListIndexedCollection instance
		 */
		public ElementsGetterLLIC(LinkedListIndexedCollection<G> col) {
			this.llicRef = col;
			this.nextNode = col.first;
			this.savedModificationCount = col.modificationCount;
		}
		
		/**
		 * Checks if the LinkedListIndexedCollection has a next element.
		 * 
		 * @return boolean value true if the LinkedListIndexedCollection has a next element, false if it does not
		 * @throws ConcurrentModificationException if the LinkedListIndexedCollection was structurally modified 
		 * since the ElemetsGetter was initialized
		 */
		@Override
		public boolean hasNextElement() {
			if (this.savedModificationCount != llicRef.modificationCount) {
				throw new ConcurrentModificationException();
			}
			
			if (nextNode != null) {
				return true;
			}
			
			return false;
		}

		/**
		 * Returns the next element of the LinkedListIndexedCollection for this ElementGetter.
		 * 
		 * @return next element of the LinkedListIndexedCollection
		 * @throws NoSuchElementException when this method is called and the LinkedListIndexedCollection does not have 
		 * a next element
		 * @throws ConcurrentModificationException if the LinkedListIndexedCollection was structurally modified 
		 * since the ElemetsGetter was initialized
		 */
		@Override
		public G getNextElement() {
			if (this.savedModificationCount != llicRef.modificationCount) {
				throw new ConcurrentModificationException();
			}
			
			if (nextNode == null) {
				throw new NoSuchElementException();
			}
			
			G value = nextNode.value;
			nextNode = nextNode.next;
			return value;
		}
		
	}
	
	/**
	 * Creates a new ElementsGetter instance for an LinkedListIndexedCollection instance.
	 * 
	 * @returns reference to new ElementsGetter instance
	 */
	@Override
	public ElementsGetter<T> createElementsGetter() {
		return new ElementsGetterLLIC<T>(this);
	}
}
