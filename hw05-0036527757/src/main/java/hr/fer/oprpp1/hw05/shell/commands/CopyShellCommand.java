package hr.fer.oprpp1.hw05.shell.commands;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;
import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellIOException;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

/**
 * Copy command implementation for MyShell. 
 */
public class CopyShellCommand implements ShellCommand {

	private String commandName = "copy";
	private List<String> commandDescription = Arrays.asList(
			"COPY COMMAND - (usage: copy <path1> <path2>",
			"Copies the file from first given path to the file from second given path.\n",
			"The command takes two arguments.",
			"The first argument must be a file, while the second argument can also be a directory.\n"
		);
	
	/**
	 * Executes copy shell command.
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
		
		if (args.size() != 2) {
			env.writeln("Invalid argument input!");
			return ShellStatus.CONTINUE;
		}
		
		Path path1 = Paths.get(args.get(0));
		Path path2 = Paths.get(args.get(1));
		
		if (Files.isDirectory(path1)) {
			env.writeln("First argument should not be a directory!");
			return ShellStatus.CONTINUE;
		}
		
		if (Files.isDirectory(path2)) {
			StringBuilder sb = new StringBuilder();
			sb.append(args.get(1));
			sb.append(args.get(1).charAt(args.get(1).length()-1) == '/' ? "" : "/");
			sb.append(path1.getFileName());
			path2 = Paths.get(sb.toString());
		}

		if (Files.exists(path2)) {
			env.writeln(path2.toString() + " already exist! Overwrite file? (y/n)");
			if (!env.readLine().equals("y")) {
				env.writeln("File was not copied!");
				return ShellStatus.CONTINUE;
			}
		}
		
		try {
			InputStream input = Files.newInputStream(path1);
			OutputStream output = Files.newOutputStream(path2, StandardOpenOption.CREATE);
			
			byte[] buffer = new byte[16];
			int length;
			while((length = input.read(buffer)) > 0)
				output.write(buffer, 0, length);
			
			input.close();
			output.close();
			env.writeln("File was copied!");
			
		} catch (IOException e) {
			env.writeln("Could not copy file!");
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
