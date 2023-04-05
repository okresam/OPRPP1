package hr.fer.oprpp1.custom.collections;

/**
 * Tester interface should be implemented so that it tests objects (if they 
 * are acceptable or not).
 */
public interface Tester {
	
	/**
	 * Tests if the given object is acceptable or not.
	 * 
	 * @param obj object that is going to be tested
	 * @return boolean value telling if the object is accepted or not
	 */
	abstract boolean test(Object obj);
}
