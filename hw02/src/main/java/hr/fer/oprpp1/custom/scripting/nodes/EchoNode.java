package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.scripting.elems.Element;

/**
 * Node that stores an echo tag.
 */
public class EchoNode extends Node{

	/**
	 * Elements of the echo node.
	 */
	private Element[] elements;
	
	/**
	 * Constructor for the echo node. Sets the elements array to the given array.
	 * 
	 * @param elements elements of the echo node
	 */
	public EchoNode(Element[] elements) {
		if (elements == null) {
			throw new NullPointerException();
		}
		
		this.elements = elements;
	}
	
	/**
	 * Elements getter.
	 * 
	 * @return array of all elements in the echo node
	 */
	public Element[] getElements() {
		return this.elements;
	}
	
	/**
	 * Returns a string representation of the echo node.
	 */
	@Override
	public String toString() {
		String str = "";
		for (int i = 0; i < this.elements.length; i++) {
			str += this.elements[i].asText() + " ";
		}
		
		return "{$= " + str + " $}"; 
	}
}
