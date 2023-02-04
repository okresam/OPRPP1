package hr.fer.oprpp1.custom.scripting.lexer;

/**
 * Exception that occurs when something wrong happens with the SmartScriptLexer.
 */
public class SmartScriptLexerException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor for SmartScriptLexerException.
	 */
	public SmartScriptLexerException() {
		
	}
	
	/**
	 * Constructor for SmartScriptLexerException with a message.
	 * 
	 * @param message message that is given when the exception occurs
	 */
	public SmartScriptLexerException(String message) {
		super(message);
	}
}
