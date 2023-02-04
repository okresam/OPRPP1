package hr.fer.oprpp1.custom.collections;

/**
 * List collection, an ordered collection.
 * 
 * @param <T> - the type of the elements in the List
 */
public interface List<T> extends Collection<T>{
	
	/**
	 * Returns the element that is located on the given index in the List.
	 * 
	 * @param index - integer value of the index of the wanted element 
	 * @return element that is located on the given index in the List
	 */
	T get(int index);
	
	/**
	 * Inserts the given argument on the given position in the List.
	 * 
	 * @param value - element that we want to insert
	 * @param position - position at which the element will be inserted
	 */
	void insert(T value, int position);
	
	/**
	 * Finds the index of the given object in the List.
	 * 
	 * @param value - object of which we want the index
	 * @return integer value of the index of the given object,
	 * returns -1 if the given element is not in the List
	 */
	int indexOf(Object value);
	
	/**
	 * Removes the element at the given index in the List.
	 * 
	 * @param index - integer value of the index of the element which we want to remove
	 */
	void remove(int index);
}
