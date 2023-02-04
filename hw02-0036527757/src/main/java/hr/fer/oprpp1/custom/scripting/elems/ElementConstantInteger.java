package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Element of type integer.
 */
public class ElementConstantInteger extends Element{
	
	/**
	 * Integer value of the element.
	 */
	private int value;
	
	/**
	 * Constructor for the integer element. Sets the value of the element to the given value.
	 * 
	 * @param value integer value of the element
	 */
	public ElementConstantInteger(int value) {
		this.value = value;
	}
	
	/**
	 * Value getter.
	 * 
	 * @return value of the integer element
	 */
	public int getValue() {
		return this.value;
	}
	
	/**
	 * Returns the integer value as a string.
	 * 
	 * @return string representation of the integer value
	 */
	@Override
	public String asText() {
		return String.valueOf(this.value);
	}
}
