package hr.fer.oprpp1.hw04.lexer;

/**
 * Exception that occurs when something wrong happens with the QueryLexer.
 */
public class QueryLexerException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor for QueryLexerException.
	 */
	public QueryLexerException() {
		
	}
	
	/**
	 * Constructor for QueryLexerException with a message.
	 * 
	 * @param message - message that is given when the exception occurs
	 */
	public QueryLexerException(String message) {
		super(message);
	}
	
}
