package hr.fer.oprpp1.hw04.db;

/**
 * Object that compares two values.
 */
public interface IComparisonOperator {
	
	/**
	 * Compares two values by the comparison operator.
	 * 
	 * @param value1 - first value that will be compared
	 * @param value2 - second value that will be compared
	 * @return true if the two values satisfy the comparison operator, false otherwise
	 */
	public boolean satisfied(String value1, String value2);
}
