package hr.fer.oprpp1.hw05.shell.commands;

import java.io.File;
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
 * Tree command implementation for MyShell. 
 */
public class TreeShellCommand implements ShellCommand {

	private String commandName = "tree";
	private List<String> commandDescription = Arrays.asList(
			"TREE COMMAND - (usage: tree <path>)",
			"Prints the directory tree for the provided directory.\n"
		);
	
	/**
	 * Executes tree shell command.
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
		
		env.writeln(exploreTree(path, 0, new StringBuilder()));
		
		return ShellStatus.CONTINUE;
	}

	private String exploreTree(Path path, int level, StringBuilder sb) {
		if (!Files.isDirectory(path) && level == 0)
			return "Given path is not a directory!\n";
		
		sb.append("  ".repeat(level) + path.getFileName() + "\n");
		level++;

		for (File f : path.toFile().listFiles()) {
			if (f.isDirectory()) {
				exploreTree(f.toPath(), level, sb);
			} else {
				sb.append("  ".repeat(level) + f.getName() + "\n");
			}
		}
		
		return sb.toString();
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
