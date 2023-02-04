package hr.fer.oprpp1.hw04.db;

/**
 * Exception that occurs when something wrong happens with the QueryParser.
 */
public class QueryParserException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor for QueryParserException.
	 */
	public QueryParserException() {
		
	}
	
	/**
	 * Constructor for QueryParserException with a message.
	 * 
	 * @param message - message that is given when the exception occurs
	 */
	public QueryParserException(String message) {
		super(message);
	}
	
}
