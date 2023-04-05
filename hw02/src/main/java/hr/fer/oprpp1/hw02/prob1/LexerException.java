package hr.fer.oprpp1.hw02.prob1;

/**
 * Exception that occurs when something wrong happens with the Lexer.
 */
public class LexerException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor for LexerException.
	 */
	public LexerException() {
		
	}
	
	/**
	 * Constructor for LexerException with a message.
	 * 
	 * @param message message that is given when the exception occurs
	 */
	public LexerException(String message) {
		super(message);
	}
}
