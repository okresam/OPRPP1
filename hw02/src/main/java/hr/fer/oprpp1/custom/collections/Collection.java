package hr.fer.oprpp1.custom.collections;

/**
 * Collection represents a collection of objects. 
 */
public interface Collection {
	
	/**
	 * Should check if the collection is empty.
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
	 * Should add the given object to the collection.
	 * 
	 * @param value object which will be added to the collection
	 */
	void add(Object value);
	
	/**
	 * Should check if the given object is inside the collection.
	 * 
	 * @param value object that is checked for to see if it is within the collection 
	 * @return boolean value telling if the collection contains the given object
	 */
	boolean contains(Object value);
	
	/**
	 * Should checks if the given object is inside the collection and removes it 
	 * (if it is inside the collection).
	 * 
	 * @param value object that is checked for to see if it iw within the collection to remove it
	 * @return boolean value telling if the collection contains the given object and that it
	 *  has been removed from the collection
	 */
	boolean remove(Object value);
	
	/**
	 * Should create an array of all objects in the collection and returns it.
	 * 
	 * @return array(Object[]) of all objects in the collection
	 */
	Object[] toArray();
	
	/**
	 * Calls the process method of the given Processor on every element of the collection.
	 * 
	 * @param processor processor whose process method is called on each element of the collection
	 */
	default void forEach(Processor processor) {
		ElementsGetter getter = createElementsGetter();
		
		while (getter.hasNextElement()) {
			processor.process(getter.getNextElement());
		}
	}
	
	/**
	 * Adds all elements from another collection to this collection.
	 * 
	 * @param other collection from which all elements are added to this collection 
	 */
	default void addAll(Collection other) {
		class AddToCollectionProcessor implements Processor{
			@Override
			public void process(Object value) {
				Collection.this.add(value);
			}
		}
		
		Processor p = new AddToCollectionProcessor();
		
		other.forEach(p);
	}
	
	/**
	 * Should clear the whole collection.
	 */
	void clear();
	
	/**
	 * Should create a new instance of an ElementsGetter. Each collection class has its own implementation
	 * of the ElementsGetter.
	 * @return 
	 */
	abstract ElementsGetter createElementsGetter();
	
	/**
	 * Adds all elements from the given collection(col) which 
	 * get accepted by the given tester to this collection .
	 * 
	 * @param col the given collection from which are all elements tested by the given tester
	 * @param tester the given tester which test elements from the given collection
	 */
	default void addAllSatisfying(Collection col, Tester tester) {
		ElementsGetter getter = col.createElementsGetter();
		
		while (getter.hasNextElement()) {
			Object element = getter.getNextElement();
			
			if (tester.test(element)) {
				add(element);
			}
		}
	}
}
