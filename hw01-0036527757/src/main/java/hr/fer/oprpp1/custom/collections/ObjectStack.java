package hr.fer.oprpp1.custom.collections;

/**
 * Stack like collection implemented using ArrayIndexedCollection.
 */
public class ObjectStack {
	private ArrayIndexedCollection arr;
	
	/**
	 * Default constructor that creates a new ArrayIndexedCollection.
	 */
	public ObjectStack() {
		arr = new ArrayIndexedCollection();
	}
	
	/**
	 * Checks if the stack is empty.
	 * 
	 * @return boolean value telling if the stack is empty or not 
	 */
	public boolean isEmpty() {
		return arr.isEmpty();
	}
	
	/**
	 * Returns the size of the stack.
	 * 
	 * @return integer value size of the stack
	 */
	public int size() {
		return arr.size();
	}
	
	/**
	 * Pushes the given object on top of the stack.
	 * 
	 * @param value object that is pushed on the stack
	 */
	public void push(Object value) {
		arr.add(value);
	}
	
	/**
	 * Pops an object from top of the stack.
	 * 
	 * @return object that is on top of the stack
	 * @throws EmptyStackException
	 */
	public Object pop() {
		if (this.size() == 0) {
			throw new EmptyStackException();
		}
		
		Object o = arr.get(this.size() - 1);
		arr.remove(this.size() - 1);
		return o;
	}
	
	/**
	 * Returns the object that is on top of the stack but does not remove it from the stack.
	 * 
	 * @return object that is on top of the stack
	 * @throws EmptyStackException
	 */
	public Object peek() {
		if (this.size() == 0) {
			throw new EmptyStackException();
		}
		
		return arr.get(this.size() - 1);
	}
	
	/**
	 * Clears the whole stack.
	 */
	void clear() {
		arr.clear();
	}
}
