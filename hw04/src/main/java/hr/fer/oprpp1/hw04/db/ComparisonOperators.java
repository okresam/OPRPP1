package hr.fer.oprpp1.hw04.db;

/**
 * Concrete implementations of ComparisonOperators.
 */
public class ComparisonOperators {
	
	/**
	 * Comparison by the less operator.
	 */
	public static final IComparisonOperator LESS = (v1, v2) -> v1.compareTo(v2) < 0;
	
	/**
	 * Comparison by the less or equals operator.
	 */
	public static final IComparisonOperator LESS_OR_EQUALS = (v1, v2) -> v1.compareTo(v2) <= 0;
	
	/**
	 * Comparison by the greater operator.
	 */
	public static final IComparisonOperator GREATER = (v1, v2) -> v1.compareTo(v2) > 0;
	
	/**
	 * Comparison by the greater or equals operator.
	 */
	public static final IComparisonOperator GREATER_OR_EQUALS = (v1, v2) -> v1.compareTo(v2) >= 0;
	
	/**
	 * Comparison by the equals operator.
	 */
	public static final IComparisonOperator EQUALS = (v1, v2) -> v1.equals(v2);
	
	/**
	 * Comparison by the not equals operator.
	 */
	public static final IComparisonOperator NOT_EQUALS = (v1, v2) -> !v1.equals(v2);
	
	/**
	 * Comparison by the like operator.
	 */
	public static final IComparisonOperator LIKE = (v1, v2) -> {
		if (v2.charAt(0) == '*') {
			return v1.endsWith(v2.substring(1));
		} else if (v2.charAt(v2.length() - 1) == '*') {
			return v1.startsWith(v2.substring(0, v2.length()-1));
		}
		
		String[] args = v2.split("\\*");
		
		if (args.length != 2) {
			return v1.equals(args[0]);
		}
		
		return v1.startsWith(args[0]) && v1.endsWith(args[1]) && v1.length() >= (args[0].length() + args[1].length());
	};
}
