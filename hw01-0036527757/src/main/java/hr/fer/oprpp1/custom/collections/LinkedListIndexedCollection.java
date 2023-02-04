package hr.fer.oprpp1.custom.collections;

/**
 * Linked list-backed collection of objects.
 */
public class LinkedListIndexedCollection extends Collection{
	
	/**
	 * ListNode which has a reference to the previous and the next ListNode and a value.
	 */
	private static class ListNode {
		ListNode previous;
		ListNode next;
		Object value;
	}
	
	/**
	 * Number of stored elements in the LinkedListIndexedCollection.
	 */
	private int size;
	
	/**
	 * Reference to the first node in the LinkedListIndexedCollection.
	 */
	private ListNode first;
	
	/**
	 * Reference to the last node in the LinkedListIndexedCollection.
	 */
	private ListNode last;
	
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
	 * @param other - collection from which are elements copied to this new collection
	 */
	public LinkedListIndexedCollection(Collection other) {
		this.size = 0;
		this.addAll(other);
	}
	
	/**
	 * @throws NullPointerException - when the given value is a null reference
	 */
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
	}
	
	/**
	 * Returns the object that is located on the given index in the collection.
	 * 
	 * @param index - int value of the index of the wanted object 
	 * @return object that is located on the given index in the collection
	 * @throws IndexOutOfBoundsException - when the given index is less than zero or the given index is 
	 * bigger than the size of the LinkedListIndexedCollection minus one
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
	
	public void clear() {
		while(this.first.next != null) {
			ListNode n = this.first.next;
			this.first.next = null;
			n.previous = null;
			this.first = n;
		}
		
		this.size = 0;
	}
	
	/**
	 * Inserts given object on the given position.
	 * 
	 * @param value - object that we want to insert into the collection
	 * @param position - position at which the object will be inserted into the collection
	 * @throws IndexOutOfBoundsException - when the given index is less than zero or the given index is 
	 * bigger than the size of the LinkedListIndexedCollection minus one 
	 * @throws NullPointerException - when the given value is a null reference
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
	}
	
	/**
	 * Finds the index of the given object in the collection.
	 * 
	 * @param value - object of which we want the index in the collection
	 * @return int value of the index of the given object in the collection, returns -1 if the given
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
	 * Removes the object at the given index in the collection.
	 * 
	 * @param index - int value of the index of the position of the object which we want to remove
	 *  from the collection
	 *  @throws IndexOutOfBoundsException - when the given index is less than zero or the given index is 
	 * bigger than the size of the LinkedListIndexedCollection minus one
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
	}
	
	public int size() {
		return this.size;
	}
	
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
				return true;
			}
			n = n.next;
		}
		return false;
	}
	
	public Object[] toArray() {
		Object[] arr = new Object[this.size];
		ListNode n = this.first;
		for (int i = 0; i < this.size; i++) {
			arr[i] = n.value;
			n = n.next;
		}
		return arr;
	}
	
	public void forEach(Processor processor) {
		ListNode n = this.first;
		while (n != null) {
			processor.process(n.value);
			n = n.next;
		}
	}
}
