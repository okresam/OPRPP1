package hr.fer.oprpp1.custom.scripting.parser;

/**
 * Exception that occurs when something wrong happens with the SmartScriptParser.
 */
public class SmartScriptParserException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor for SmartScriptParserException.
	 */
	public SmartScriptParserException() {
		
	}
	
	/**
	 * Constructor for SmartScriptParserException with a message.
	 * 
	 * @param message message that is given when the exception occurs
	 */
	public SmartScriptParserException(String message) {
		super(message);
	}
}
