package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Element of type double.
 */
public class ElementConstantDouble extends Element{
	
	/**
	 * Double value of the element.
	 */
	private double value;
	
	/**
	 * Constructor for the double element. Sets the value of the element to the given value.
	 * 
	 * @param value double value of the element
	 */
	public ElementConstantDouble(double value) {
		this.value = value;
	}
	
	/**
	 * Value getter.
	 * 
	 * @return value of the double element
	 */
	public double getValue() {
		return this.value;
	}
	
	/**
	 * Returns the double value as a string.
	 * 
	 * @return string representation of the double value
	 */
	@Override
	public String asText() {
		return String.valueOf(this.value);
	}
}
