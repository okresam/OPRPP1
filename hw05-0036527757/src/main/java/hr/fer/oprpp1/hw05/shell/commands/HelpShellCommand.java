package hr.fer.oprpp1.hw05.shell.commands;

import java.util.Arrays;
import java.util.List;
import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellIOException;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

/**
 * Help command implementation for MyShell. 
 */
public class HelpShellCommand implements ShellCommand {

	private String commandName = "help";
	private List<String> commandDescription = Arrays.asList(
			"HELP COMMAND - (usage: help or help <command>)",
			"If no input arguments are given, lists all available commands.",
			"When a command name is given as input argument, prints the command description for the given command.\n"
		);
	
	/**
	 * Executes help shell command.
	 * 
	 * @throws ShellIOException if reading or writing through the environment is not possible
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		arguments = arguments.trim();
		if (arguments.equals("")) {
			for (String command : env.commands().keySet()) {
				env.writeln(command);
			}
		} else {
			ShellCommand command = env.commands().get(arguments);
			if (command == null) {
				env.writeln("Command does not exist!");
			} else {
				for (String sl : command.getCommandDescription()) {
					env.writeln(sl);
				}
			}
		}
		
		return ShellStatus.CONTINUE;
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
