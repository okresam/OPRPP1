package hr.fer.oprpp1.hw04.db;

/**
 * Object that represents a conditional expression of a query.
 */
public class ConditionalExpression {

	/**
	 * FieldValueGetter of the ConditionalExpression
	 */
	IFieldValueGetter fieldGetter;
	
	/**
	 * String literal of the ConditionalExpression
	 */
	String stringLiteral;
	
	/**
	 * ComaprisonOperator of the ConditionalExpression
	 */
	IComparisonOperator comparisonOperator;
	
	/**
	 * Constructor for the ConditionalExpression. Sets the attributes to the given values.
	 * 
	 * @param fieldGetter - FieldValueGetter to be set
	 * @param stringLiteral - String literal to be set
	 * @param comparisonOperator - ComaprisonOperator to be set
	 */
	public ConditionalExpression(IFieldValueGetter fieldGetter, String stringLiteral, IComparisonOperator comparisonOperator) {
		this.fieldGetter = fieldGetter;
		this.stringLiteral = stringLiteral;
		this.comparisonOperator = comparisonOperator;
	}

	/**
	 * Getter for the field value getter.
	 * 
	 * @return FieldValueGetter of the expression.
	 */
	public IFieldValueGetter getFieldGetter() {
		return fieldGetter;
	}

	/**
	 * Getter for the string literal.
	 * 
	 * @return string literal of the expression.
	 */
	public String getStringLiteral() {
		return stringLiteral;
	}

	/**
	 * Getter for the comparison operator.
	 * 
	 * @return ComparisonOperator of the expression.
	 */
	public IComparisonOperator getComparisonOperator() {
		return comparisonOperator;
	}
	
}
