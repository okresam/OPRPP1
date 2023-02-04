package hr.fer.oprpp1.hw05.shell.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellIOException;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

/**
 * Mkdir command implementation for MyShell. 
 */
public class MkdirShellCommand implements ShellCommand {

	private String commandName = "mkdir";
	private List<String> commandDescription = Arrays.asList(
			"MKDIR COMMAND - (usage: mkdir <path>)",
			"Creates correct directory structure for the given path.\n"
		);
	
	/**
	 * Executes mkdir shell command.
	 * 
	 * @throws ShellIOException if reading or writing through the environment is not possible
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		List<String> args;
		
		try {
			CommandArgumentParser parser = new CommandArgumentParser(arguments, true); 
			args = parser.getArguments();
		} catch(Exception e) {
			env.writeln("Invalid path input.");
			return ShellStatus.CONTINUE;
		}
		
		if (args.size() != 1) {
			env.writeln("Invalid argument input!");
			return ShellStatus.CONTINUE;
		}
		
		Path path = Paths.get(args.get(0));
		try {
			Files.createDirectories(path);
			env.writeln("");
		} catch (IOException e) {
			env.writeln("Could not create directory!");
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
