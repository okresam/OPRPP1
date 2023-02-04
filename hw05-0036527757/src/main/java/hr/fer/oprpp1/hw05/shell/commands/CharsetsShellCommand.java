package hr.fer.oprpp1.hw05.shell.commands;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellIOException;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

/**
 * Charsets command implementation for MyShell. 
 */
public class CharsetsShellCommand implements ShellCommand{

	private String commandName = "charsets";
	private List<String> commandDescription = Arrays.asList(
			"CHARSETS COMMAND - (usage: charsets)",
			"Lists names of supported charsets for your Java platform.\n"
		);
	
	/**
	 * Executes charsets shell command.
	 * 
	 * @throws ShellIOException if reading or writing through the environment is not possible
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		for (Charset cs : Charset.availableCharsets().values()) {
			env.writeln(cs.toString());
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
