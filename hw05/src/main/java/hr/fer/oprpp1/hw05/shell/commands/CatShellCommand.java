package hr.fer.oprpp1.hw05.shell.commands;

import java.io.InputStream;
import java.nio.charset.Charset;
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
 * Cat command implementation for MyShell.
 */
public class CatShellCommand implements ShellCommand {

	private String commandName = "cat";
	private List<String> commandDescription = Arrays.asList(
			"CAT COMMAND - (usage: cat <path> <charset> or cat <path>)",
			"Cat command opens the given file and writes its content to console.\n",
			"The command takes one or two arguments.",
			"The first argument is a path to some file and is mandatory.",
			"The second argument is charset name that should be used to interpret chars from bytes.",
			"If the second argument is not provided, the default platform charset is used.\n"
		);
	
	/**
	 * Executes cat shell command.
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
		
		if (args.size() != 1 && args.size() != 2) {
			env.writeln("Invalid argument input!");
			return ShellStatus.CONTINUE;
		}
		
		try {
			Path path = Paths.get(args.get(0));
			InputStream input = Files.newInputStream(path);
			Charset charSet;
			
			if (args.size() == 1) charSet = Charset.defaultCharset();
			else charSet = Charset.forName(args.get(1));
			
			byte[] inBytes = input.readAllBytes();
			
			String output = new String(inBytes, charSet);
			env.write(output);
			
		} catch (Exception e) {
			env.writeln("Invalid input arguments or file does not exist!");
			return ShellStatus.CONTINUE;
		}
		
		env.writeln("");
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
