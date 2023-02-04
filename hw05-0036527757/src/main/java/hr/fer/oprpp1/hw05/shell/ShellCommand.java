package hr.fer.oprpp1.hw05.shell;

import java.util.List;

/**
 * Interface model for a shell command.
 */
public interface ShellCommand {

	/**
	 * Executes the shell command.
	 * 
	 * @param env - environment of MyShell
	 * @param arguments - input arguments for the command as a single string
	 * @return ShellStatus after executing the command
	 */
	ShellStatus executeCommand(Environment env, String arguments);
	
	/**
	 * Getter for command name.
	 * 
	 * @return name of the command
	 */
	String getCommandName();
	
	/**
	 * Getter for command description.
	 * 
	 * @return List of strings about description of the command
	 */
	List<String> getCommandDescription();

}
