package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Element of type string.
 */
public class ElementString extends Element{
	
	/**
	 * String value of the element.
	 */
	private String value;
	
	/**
	 * Constructor for the string element. Sets the value of the element to the given value.
	 * 
	 * @param value string value of the element
	 */
	public ElementString(String value) {
		this.value = value;
	}
	
	/**
	 * Value getter.
	 * 
	 * @return value of the string element
	 */
	public String getValue() {
		return this.value;
	}
	
	/**
	 * Returns the string value.
	 * 
	 * @return string value of the element
	 */
	@Override
	public String asText() {
		return "\"" + this.value + "\"";
	}
}
