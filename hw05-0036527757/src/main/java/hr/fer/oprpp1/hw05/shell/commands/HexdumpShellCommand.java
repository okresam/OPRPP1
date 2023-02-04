package hr.fer.oprpp1.hw05.shell.commands;

import java.io.IOException;
import java.io.InputStream;
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
 * Hexdump command implementation for MyShell. 
 */
public class HexdumpShellCommand implements ShellCommand {

	private String commandName = "hexdump";
	private List<String> commandDescription = Arrays.asList(
			"HEXDUMP COMMAND - (usage: hexdump <path>)",
			"Prints hexadecimal view for the given file.\n"
		);
	
	/**
	 * Executes hexdump shell command.
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
			InputStream input = Files.newInputStream(path);
			
			byte[] buffer = new byte[16];
			int length;
			int line = 0;
			StringBuilder characters;
			while ((length = input.read(buffer)) != -1) {
				characters = new StringBuilder();
				env.write(String.format("%08d: ", line));
				line += 10;
				for (int i = 0; i < 16; i++) {
					if (i < length) {
						env.write(String.format("%02X", buffer[i]));
						characters.append((buffer[i] >= 32 && buffer[i] <= 127) ? (char)buffer[i] : ".");
					} else {
						env.write("  ");
					}
					if (i == 7) {
						env.write("|");
					} else {
						env.write(" ");				
					}
				}
				env.write("| ");
				env.writeln(characters.toString());
			}
			
			input.close();
		} catch (IOException e) {
			env.writeln("Error while trying to read the file!");
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
