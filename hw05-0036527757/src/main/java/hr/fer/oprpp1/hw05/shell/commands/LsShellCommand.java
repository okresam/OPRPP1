package hr.fer.oprpp1.hw05.shell.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellIOException;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

/**
 * Ls command implementation for MyShell. 
 */
public class LsShellCommand implements ShellCommand {

	private String commandName = "ls";
	private List<String> commandDescription = Arrays.asList(
			"LS COMMAND - (usage: ls <path>)",
			"Takes a directory path as argument and prints the directory listing.\n"
		);
	
	/**
	 * Executes ls shell command.
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
			List<Path> files = Files.list(path).collect(Collectors.toList());
			
			StringBuilder output = new StringBuilder();
			
			for (Path p : files) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				BasicFileAttributeView faView = Files.getFileAttributeView(
				p, BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS
				);
				BasicFileAttributes attributes = faView.readAttributes();
				FileTime fileTime = attributes.creationTime();
				String formattedDateTime = sdf.format(new Date(fileTime.toMillis()));
				
				output.append(String.format("%c%c%c%c %10d %s %s\n",
						Files.isDirectory(p) ? 'd' : '-',
						Files.isReadable(p) ? 'r' : '-',
						Files.isWritable(p) ? 'w' : '-',
						Files.isExecutable(p) ? 'x' : '-',
						Files.size(p),
						formattedDateTime,
						p.getFileName().toString()));
			}
			env.writeln(output.toString());
		} catch (IOException e) {
			env.writeln("Error while trying to read directory contents!");
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
