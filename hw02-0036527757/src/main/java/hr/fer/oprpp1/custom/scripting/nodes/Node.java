package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;

/**
 * Base node class.
 */
public class Node {
	
	/**
	 * ArrayIndexedCollection which stores children of the node.
	 */
	ArrayIndexedCollection nodes;
	
	/**
	 * Adds the given node as the child of this node.
	 * 
	 * @param child node that will become the child of this node
	 */
	public void addChildNode(Node child) {
		if (child == null) {
			throw new NullPointerException();
		}
		
		if (nodes == null) {
			nodes = new ArrayIndexedCollection();
		}
		
		nodes.add(child);
	}
	
	/**
	 * Returns the number of children of this node
	 * 
	 * @return integer value number of children
	 */
	public int numberOfChildren() {
		return this.nodes.size();
	}
	
	/**
	 * Gets the child of this node that is located on the given index.
	 * 
	 * @param index index of the child to be returned
	 * @return child node on the given index
	 */
	public Node getChild(int index) {
		if (index < 0 || index > this.nodes.size() - 1) {
			throw new IndexOutOfBoundsException();
		}
		
		return (Node)this.nodes.get(index);
	}
	
}
