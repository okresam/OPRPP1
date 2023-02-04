package hr.fer.oprpp1.custom.demo;

import hr.fer.oprpp1.custom.collections.Tester;

/**
 * Tester that accepts even integers.
 */
public class EvenIntegerTester implements Tester{
	
	/**
	 * Tests if the given object is an even integer.
	 */
	public boolean test(Object obj) {
		if (!(obj instanceof Integer)) return false;
		
		Integer i = (Integer) obj;
		
		return i % 2 == 0;
	}
}
