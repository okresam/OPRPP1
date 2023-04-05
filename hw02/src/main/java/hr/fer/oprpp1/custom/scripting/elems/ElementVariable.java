package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Element of type variable.
 */
public class ElementVariable extends Element{
	
	/**
	 * Name of the variable.
	 */
	private String name;
	
	/**
	 * Constructor for the variable element. Sets the name of the variable to the given value.
	 * 
	 * @param name the name of the variable 
	 */
	public ElementVariable(String name) {
		this.name = name;
	}
	
	/**
	 * Variable name getter.
	 * 
	 * @return name of the variable 
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Returns the variable name.
	 * 
	 * @return string name of the variable.
	 */
	@Override
	public String asText() {
		return this.name;
	}
}
