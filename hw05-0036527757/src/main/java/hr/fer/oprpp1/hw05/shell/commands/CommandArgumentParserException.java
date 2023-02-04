package hr.fer.oprpp1.hw05.shell.commands;

/**
 * Exception that occurs when the command argument parser receives invalid input.
 */
public class CommandArgumentParserException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor for CommandArgumentParserException.
	 */
	public CommandArgumentParserException() {
		
	}
	
	/**
	 * Constructor for CommandArgumentParserException with a message.
	 * 
	 * @param message - message that is given when the exception occurs
	 */
	public CommandArgumentParserException(String message) {
		super(message);
	}
	
}
