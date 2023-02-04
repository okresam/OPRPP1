package hr.fer.oprpp1.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

/**
 * Linked list-backed collection of objects.
 */
public class LinkedListIndexedCollection implements List{
	
	/**
	 * ListNode which has a reference to the previous and the next ListNode and a value.
	 */
	private static class ListNode {
		ListNode previous;
		ListNode next;
		Object value;
	}
	
	/**
	 * Number of elements currently stored in the LinkedListIndexedCollection.
	 */
	private int size;
	/**
	 * First ListNode in the LinkedListIndexedCollcetion.
	 */
	private ListNode first;
	/**
	 * Last ListNode in the LinkedListIndexedCollection.
	 */
	private ListNode last;
	
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
	 * @param other collection from which are elements copied to this new collection
	 */
	public LinkedListIndexedCollection(Collection other) {
		this.size = 0;
		this.addAll(other);
	}
	
	/**
	 * Adds the given object to the end of the LinkedListIndexedCollection.
	 * 
	 * @throws NullPointerException when the given object is a null reference
	 */
	@Override
	public void add(Object value) {
		if (value == null) {
			throw new NullPointerException();
		}
		
		if (this.first == null && this.last == null) {
			ListNode n = new ListNode();
			n.value = value;
			
			this.first = n;
			this.last = n;
		} else {
			ListNode n = new ListNode();
			n.value = value;
			
			n.previous = this.last;
			this.last.next = n;
			this.last = n;
		}
		
		this.size++;
		modificationCount++;
	}
	
	/**
	 * Returns the object that is located on the given index in the LinkedListIndexedCollection.
	 * 
	 * @param index int value of the index of the wanted object
	 * @return object that is located on the given index
	 * @throws IndexOutOfBoundsException when the given index is smaller than 0 or bigger
	 * than the size of the LinkedListIndexedCollection - 1
	 */
	public Object get(int index) {
		if (index < 0 || index > this.size - 1) {
			throw new IndexOutOfBoundsException();
		}
		
		ListNode n;
		
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
			ListNode n = this.first.next;
			this.first.next = null;
			n.previous = null;
			this.first = n;
		}
		
		this.size = 0;
		
		modificationCount++;
	}
	
	/**
	 * Inserts given object on the given position in the LinkedListIndexedCollection.
	 * 
	 * @param value object that we want to insert
	 * @param position position at which the object will be inserted
	 * @throws IndexOutOfBoundsException when the given position is smaller than 0 or bigger
	 * than the size of the LinkedListIndexedCollection - 1
	 * @throws NullPointerException when the given object is a null reference
	 */
	public void insert(Object value, int position) {
		if (position < 0 || position > this.size) {
			throw new IndexOutOfBoundsException();
		}
		
		if (value == null ) {
			throw new NullPointerException();
		}
		
		if (position == this.size) {
			ListNode newNode = new ListNode();
			newNode.value = value;
			
			this.last.next = newNode;
			newNode.previous = this.last;
			
			this.last = newNode;
		} else if(position == 0) {
			ListNode newNode = new ListNode();
			newNode.value = value;
			
			this.first.previous = newNode;
			newNode.next = this.first;
			
			this.first = newNode;
		} else {
			ListNode positionNode = this.first;
			for (int i = 0; i < position; i++) {
				positionNode = positionNode.next;
			}
			ListNode previousNode = positionNode.previous;
			ListNode newNode = new ListNode();
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
	 * @param value object of which we want the index
	 * @return Int value of the index of the given object. Returns -1 if the given
	 * object is not in the collection
	 */
	public int indexOf(Object value) {
		ListNode n = this.first;
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
	 * Removes the object at the given index in the LinkedListIndexedCollection.
	 * 
	 * @param index int value of the index of the object which we want to remove
	 *  @throws IndexOutOfBoundsException if the given index is smaller than 0 or bigger than
	 *  the size of the LinkedListIndexedCollection - 1
	 */
	public void remove(int index) {
		if (index < 0 || index > this.size - 1) {
			throw new IndexOutOfBoundsException();
		}
		
		if (index == this.size - 1) {
			ListNode nodeBeforeLast = this.last.previous;
			nodeBeforeLast.next = null;
			this.last.previous = null;
			this.last = nodeBeforeLast;
		}else if (index == 0) {
			ListNode nodeAfterFirst = this.first.next;
			nodeAfterFirst.previous = null;
			this.first.next = null;
			this.first = nodeAfterFirst;
		} else {
			ListNode indexNode = this.first;
			for (int i = 0; i < index; i++) {
				indexNode = indexNode.next;
			}
			ListNode previousNode = indexNode.previous;
			ListNode nextNode = indexNode.next;
		
			previousNode.next = nextNode;
			nextNode.previous = previousNode;
			
			indexNode.next = null;
			indexNode.previous = null;
		}
		
		this.size--;
		
		modificationCount++;
	}
	
	/**
	 * Returns the size of the LinkedListIndexedCollection.
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
		ListNode n = this.first;
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
		ListNode n = this.first;
		while(n != null) {
			if (n.value.equals(value)) {
				if (n == this.last) {
					ListNode nodeBeforeLast = this.last.previous;
					nodeBeforeLast.next = null;
					this.last.previous = null;
					this.last = nodeBeforeLast;
				}else if (n == this.first) {
					ListNode nodeAfterFirst = this.first.next;
					nodeAfterFirst.previous = null;
					this.first.next = null;
					this.first = nodeAfterFirst;
				} else {
					ListNode previousNode = n.previous;
					ListNode nextNode = n.next;
				
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
		ListNode n = this.first;
		for (int i = 0; i < this.size; i++) {
			arr[i] = n.value;
			n = n.next;
		}
		return arr;
	}
	
	/**
	 *  Class that implements an ElementsGetter for the LinkedListIndexedCollection.
	 */
	private static class ElementsGetterLLIC implements ElementsGetter{
		
		/**
		 * Reference to a ListNode of the LinkedListIndexedCollection.
		 */
		private ListNode nextNode;
		
		/**
		 * Reference to the LinkedListIndexedCollection instance.
		 */
		LinkedListIndexedCollection llicRef;
		
		/**
		 * Number of saved modifications when the ElementsGetter was initialized.
		 */
		private long savedModificationCount;
		
		/**
		 * Constructor for the ElementsGetterLLIC. Sets the LinkedListIndexedCollection reference of the class 
		 * to the given reference and sets the reference to the first node in the given LinkedListIndexedCollection.
		 * 
		 * @param col reference to an LinkedListIndexedCollection instance
		 */
		public ElementsGetterLLIC(LinkedListIndexedCollection col) {
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
		 * @return object which is the next element
		 * @throws NoSuchElementException when this method is called and the LinkedListIndexedCollection does not have 
		 * a next element
		 * @throws ConcurrentModificationException if the LinkedListIndexedCollection was structurally modified 
		 * since the ElemetsGetter was initialized
		 */
		@Override
		public Object getNextElement() {
			if (this.savedModificationCount != llicRef.modificationCount) {
				throw new ConcurrentModificationException();
			}
			
			if (nextNode == null) {
				throw new NoSuchElementException();
			}
			
			Object value = nextNode.value;
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
	public ElementsGetter createElementsGetter() {
		return new ElementsGetterLLIC(this);
	}
}
