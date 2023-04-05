package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Element of type operator.
 */
public class ElementOperator extends Element{
	
	/**
	 * Symbol that represents the operator.
	 */
	private String symbol;
	
	/**
	 * Constructor for the operator element. Sets the operator symbol of the element to the given value.
	 * 
	 * @param symbol the operator symbol 
	 */
	public ElementOperator(String symbol) {
		this.symbol = symbol;
	}
	
	/**
	 * Operator symbol getter.
	 * 
	 * @return symbol that represents the operator
	 */
	public String getSymbol() {
		return this.symbol;
	}
	
	/**
	 * Returns the operator symbol.
	 * 
	 * @return string symbol of the operator
	 */
	@Override
	public String asText() {
		return this.symbol;
	}
}
