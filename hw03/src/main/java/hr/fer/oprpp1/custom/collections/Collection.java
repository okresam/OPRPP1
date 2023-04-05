package hr.fer.oprpp1.custom.collections;

/**
 * Collection represents a group of objects. 
 * 
 * @param <T> - the type of elements in the collection
 */
public interface Collection<T> {
	
	/**
	 * Checks if the collection is empty.
	 * 
	 * @return boolean value telling if the collection is empty or not 
	 */
	default boolean isEmpty() {
		return this.size() == 0;
	}
	
	/**
	 * Returns the size of the collection.
	 * 
	 * @return integer value size of the collection
	 */
	
	int size();
	
	/**
	 * Adds the given argument to the collection.
	 * 
	 * @param value - element which will be added to the collection
	 */
	void add(T value);
	
	/**
	 * Checks if the given object is inside the collection.
	 * 
	 * @param value - object that is checked if it is within the collection 
	 * @return boolean value telling if the collection contains the given object
	 */
	boolean contains(Object value);
	
	/**
	 * Checks if the given object is inside the collection and removes it 
	 * (if it is inside the collection).
	 * 
	 * @param value - object that is checked if it is within the collection to remove it
	 * @return boolean value telling if the collection contains the given object and that it
	 *  has been removed from the collection
	 */
	boolean remove(Object value);
	
	/**
	 * Creates an object array of all elements in the collection.
	 * 
	 * @return object array of all elements in the collection
	 */
	Object[] toArray();
	
	/**
	 * Creates an array of all elements in the collection. If the given array does not have enough space,
	 * a new bigger array is created.
	 * 
	 * @param arr - the array in which the elements will be stored if it has enough space
	 * @return array of all elements of the collection
	 */
	T[] toArray(T[] arr);
	
	/**
	 * Calls the process method of the given Processor on every element of the collection.
	 * 
	 * @param processor - processor whose process method is called on each element of the collection
	 */
	default void forEach(Processor<? super T> processor) {
		ElementsGetter<T> getter = createElementsGetter();
		
		while (getter.hasNextElement()) {
			processor.process(getter.getNextElement());
		}
	}
	
	/**
	 * Adds all elements from another collection to this collection.
	 * 
	 * @param other - collection from which all elements are added to this collection 
	 */
	default void addAll(Collection<? extends T> other) {
		
		class AddToCollectionProcessor implements Processor<T>{
			@Override
			public void process(T value) {
				Collection.this.add(value);
			}
		}
		
		AddToCollectionProcessor p = new AddToCollectionProcessor();
		
		other.forEach(p);
	}
	
	/**
	 * Clears the whole collection.
	 */
	void clear();
	
	/**
	 * Creates a new instance of an ElementsGetter, a object that iterates through the collection.
	 * 
	 * @return new ElementsGetter instance for this collection
	 */
	abstract ElementsGetter<T> createElementsGetter();
	
	/**
	 * Adds all elements from the given collection which 
	 * get accepted by the given tester to this collection .
	 * 
	 * @param col - the collection from which are all elements tested by the given tester
	 * @param tester - the tester which tests elements from the given collection
	 */
	default void addAllSatisfying(Collection<? extends T> col, Tester<? super T> tester) {
		ElementsGetter<? extends T> getter = col.createElementsGetter();
		
		while (getter.hasNextElement()) {
			T element = getter.getNextElement();
			
			if (tester.test(element)) {
				add(element);
			}
		}
	}
	
}
