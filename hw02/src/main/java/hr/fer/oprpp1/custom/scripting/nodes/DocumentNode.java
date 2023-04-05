package hr.fer.oprpp1.custom.scripting.nodes;

/**
 * Node that stores a parsed document.
 */
public class DocumentNode extends Node{
	
	/**
	 * Indicates whether this DocumentNode is equal to the given DocumentNode.
	 */
	@Override
	public boolean equals(Object o) {
		DocumentNode dn = (DocumentNode) o;
		return this.toString().equals(dn.toString());
	}
	
	/**
	 * Returns a string representation of the document node.
	 */
	@Override
	public String toString() {
		String docString = "";
		try {
			this.numberOfChildren();
		} catch (Exception e){
			return docString;
		}
		for (int i = 0; i < this.numberOfChildren(); i++) {
			try {
				this.getChild(i).numberOfChildren();
				docString += this.getChild(i).toString() + this.getAllChildValuesAsString(this.getChild(i));
				docString += "{$END$}";
			} catch (Exception e) {
				docString += this.getChild(i).toString(); 
			}
		}
		
		return docString;
	}
	
	/**
	 * Combines string values of the given node and all its descendants.
	 * 
	 * @param n given node of which we want the string value
	 * @return string value of a node and all its descendants
	 */
	private String getAllChildValuesAsString(Node n) {
		String str = "";
		
		for (int i = 0; i < n.numberOfChildren(); i++) {
			try {
				n.getChild(i).numberOfChildren();
				str += n.getChild(i).toString() + this.getAllChildValuesAsString(n.getChild(i));
				str += "{$END$}";
			} catch (Exception e) {
				str += n.getChild(i).toString();
			}
		}
		
		return str;
	}
}
