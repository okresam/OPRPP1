package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Element of type function.
 */
public class ElementFunction extends Element{

	/**
	 * Name of the function.
	 */
	private String name;
	
	/**
	 * Constructor for the function element. Sets the name of the function to the given value.
	 * 
	 * @param name the name of the function 
	 */
	public ElementFunction(String name) {
		this.name = name;
	}
	
	/**
	 * Function name getter.
	 * 
	 * @return name of the function 
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Returns the function name.
	 * 
	 * @return string name of the function.
	 */
	@Override
	public String asText() {
		return this.name;
	}
}
