package hr.fer.oprpp1.custom.scripting.nodes;

/**
 * Node that stores text.
 */
public class TextNode extends Node{
	
	/**
	 * Stored text in the node.
	 */
	private String text;
	
	/**
	 * Constructor for text node. Sets the text of the node to the given text.
	 * 
	 * @param text text of the node
	 */
	public TextNode(String text) {
		this.text = text;
	}
	
	/**
	 * Text getter.
	 * 
	 * @return text of the node
	 */
	public String getText() {
		return this.text;
	}
	
	/**
	 * Returns a string representation of the text node.
	 */
	@Override
	public String toString() {
		return this.text;
	}
}
