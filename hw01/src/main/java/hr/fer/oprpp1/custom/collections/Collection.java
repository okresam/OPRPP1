package hr.fer.oprpp1.custom.collections;

/**
 * Class Collection represents a collection of objects. 
 */
public class Collection {
	
	/**
	 * Default constructor for class Collection.
	 */
	protected Collection() {
		
	}
	
	/**
	 * Checks if the collection is empty.
	 * 
	 * @return boolean value telling if the collection is empty or not 
	 */
	public boolean isEmpty() {
		return this.size() == 0;
	}
	
	/**
	 * Returns the size of the collection.
	 * 
	 * @return integer value size of the collection
	 */
	public int size() {
		return 0;
	}
	
	/**
	 * Adds the given object to the collection.
	 * 
	 * @param value object which will be added to the collection
	 */
	public void add(Object value) {
		
	}
	
	/**
	 * Checks if the given object is inside the collection.
	 * 
	 * @param value object that is checked for to see if it is within the collection 
	 * @return boolean value telling if the collection contains the given object
	 */
	public boolean contains(Object value) {
		return false;
	}
	
	/**
	 * Checks if the given object is inside the collection and removes it 
	 * (if it is inside the collection).
	 * 
	 * @param value object that is checked for to see if it iw within the collection to remove it
	 * @return boolean value telling if the collection contains the given object and that it
	 *  has been removed from the collection
	 */
	public boolean remove(Object value) {
		return false;
	}
	
	/**
	 * Creates an array of all objects in the collection and returns it.
	 * 
	 * @return array(Object[]) of all objects in the collection
	 */
	public Object[] toArray() {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Calls the process method of the given Processor on every element of the collection.
	 * 
	 * @param processor processor whose process method is called on each element of the collection
	 */
	public void forEach(Processor processor) {
		
	}
	
	/**
	 * Adds all elements from another collection to this collection.
	 * 
	 * @param other collection from which all elements are added to this collection 
	 */
	public void addAll(Collection other) {
		class AddToCollectionProcessor extends Processor{
			public void process(Object value) {
				Collection.this.add(value);
			}
		}
		
		Processor p = new AddToCollectionProcessor();
		
		other.forEach(p);
	}
	
	/**
	 * Clears the whole collection.
	 */
	public void clear() {
		
	}
}
