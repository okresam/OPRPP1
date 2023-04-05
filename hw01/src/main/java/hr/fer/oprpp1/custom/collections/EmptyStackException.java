package hr.fer.oprpp1.custom.collections;

/**
 * Exception that happens when the stack is empty and methods pop() or peek() are called.
 */
public class EmptyStackException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor for EmptyStackException.
	 */
	public EmptyStackException() {
		
	}
	
	/**
	 * Constructor for EmptyStackException with a message.
	 *  
	 * @param message message that is given when the exception occurs
	 */
	public EmptyStackException(String message) {
		super(message);
	}
}
