package hr.fer.oprpp1.hw05.shell;

/**
 * Exception that occurs when reading or writing by the shell fails.
 */
public class ShellIOException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor for ShellIOException.
	 */
	public ShellIOException() {
		
	}
	
	/**
	 * Constructor for ShellIOException with a message.
	 * 
	 * @param message - message that is given when the exception occurs
	 */
	public ShellIOException(String message) {
		super(message);
	}
	
}
