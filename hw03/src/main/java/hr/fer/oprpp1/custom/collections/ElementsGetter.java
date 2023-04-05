package hr.fer.oprpp1.custom.collections;

/**
 * An ElementsGetter fetches and returns elements of an collection. </br>
 * Each specific collection has its own implementation of the ElementsGetter.
 */
public interface ElementsGetter<T> {
	/**
	 * Checks if the collection has a next element.
	 * 
	 * @return boolean value true if the collection has a next element, false if it does not
	 */
	boolean hasNextElement();
	
	/**
	 * Returns the next element of the collection.
	 * 
	 * @return object which is the next element
	 */
	T getNextElement();
	
	/**
	 * Calls the process method of the given Processor on all remaining elements
	 * of the ElementsGetter.
	 * 
	 * @param p Processor whose process method will be called on all 
	 * remaining elements of a collection
	 */
	default void processRemaining(Processor<? super T> p) {
		while (hasNextElement()) {
			p.process(getNextElement());
		}
	}
}
