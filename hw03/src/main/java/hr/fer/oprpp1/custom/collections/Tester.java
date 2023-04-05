package hr.fer.oprpp1.custom.collections;

/**
 * An Tester tests the given elements. (Tests if the given element is acceptable or not).
 */
public interface Tester<T> {
	
	/**
	 * Tests if the given argument is acceptable or not.
	 * 
	 * @param t - input argument
	 * @return boolean value telling if the object is accepted or not
	 */
	abstract boolean test(T t);
}
