package hr.fer.oprpp1.hw05.shell.commands;

import java.util.Arrays;
import java.util.List;
import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

/**
 * Exit command implementation for MyShell. 
 */
public class ExitShellCommand implements ShellCommand {

	private String commandName = "exit";
	private List<String> commandDescription = Arrays.asList(
			"EXIT COMMAND - (usage: exit)",
			"Terminates the shell.\n"
		);
	
	/**
	 * Executes exit shell command.
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		return ShellStatus.TERMINATE;
	}

	@Override
	public String getCommandName() {
		return commandName;
	}

	@Override
	public List<String> getCommandDescription() {
		return commandDescription;
	}

}
