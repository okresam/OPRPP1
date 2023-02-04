package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.scripting.elems.Element;
import hr.fer.oprpp1.custom.scripting.elems.ElementVariable;

/**
 * Node that stores an for loop tag.
 */
public class ForLoopNode extends Node{
	
	/**
	 * Variable of the for loop.
	 */
	private ElementVariable variable;
	
	/**
	 * Start expression of the for loop.
	 */
	private Element startExpression;
	
	/**
	 * End expression of the for loop.
	 */
	private Element endExpression;
	
	/**
	 * Step expression of the for loop.
	 */
	private Element stepExpression;
	
	/**
	 * Constructor for the for loop node. Sets the values of the for loop node elements to the given values.
	 * 
	 * @param variable value of the variable
	 * @param startExpression value of the startExpression
	 * @param endExpression value of the endExpression
	 * @param stepExpression value of the stepExpression
	 */
	public ForLoopNode(ElementVariable variable, Element startExpression, 
			Element endExpression, Element stepExpression) {
		
		if (variable == null || startExpression == null 
			|| endExpression == null) {
			
			throw new NullPointerException();
		}
		
		this.variable = variable;
		this.startExpression = startExpression;
		this.endExpression = endExpression;
		this.stepExpression = stepExpression;
	}
	
	/**
	 * Variable getter.
	 * 
	 * @return value of the variable
	 */
	public ElementVariable getVariable() {
		return this.variable;
	}
	
	/**
	 * StartExpression getter.
	 * 
	 * @return value of the startExpression
	 */
	public Element getStartExpression() {
		return this.startExpression;
	}
	
	/**
	 * EndExpression getter.
	 * 
	 * @return value of the endExpression
	 */
	public Element getEndExpression() {
		return this.endExpression;
	}
	
	/**
	 * StepExpression getter.
	 * 
	 * @return value of the stepExpression
	 */
	public Element getStepExpression() {
		return this.stepExpression;
	}
	
	/**
	 * Returns a string representation of the for loop node.
	 */
	@Override
	public String toString() {
		return "{$FOR " + this.variable.asText() + " " + this.startExpression.asText() + " " 
				+ this.endExpression.asText() + " " + (this.stepExpression == null ? "": this.stepExpression.asText()) + " $}"; 
	}
}
