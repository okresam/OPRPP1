package hr.fer.oprpp1.custom.collections;

/**
 * Stack implemented using ArrayIndexedCollection.
 *
 * @param <T> - the type of the elements in the ObjectStack
 */
public class ObjectStack<T> {
	private ArrayIndexedCollection<T> arr;
	
	/**
	 * Default constructor for the ObjectStack.
	 */
	public ObjectStack() {
		arr = new ArrayIndexedCollection<T>();
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
	 * Pushes the given argument on top of the stack.
	 * 
	 * @param value - element that is pushed on the stack
	 */
	public void push(T value) {
		arr.add(value);
	}
	
	/**
	 * Pops an element from top of the stack.
	 * 
	 * @return element that is on top of the stack
	 * @throws EmptyStackException - when the stack is empty and this method is called
	 */
	public T pop() {
		if (this.size() == 0) {
			throw new EmptyStackException();
		}
		
		T o = arr.get(this.size() - 1);
		arr.remove(this.size() - 1);
		return o;
	}
	
	/**
	 * Returns the element that is on top of the stack but does not remove it from the stack.
	 * 
	 * @return element that is on top of the stack
	 * @throws EmptyStackException - when the stack is empty and this method is called
	 */
	public T peek() {
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
