package hr.fer.oprpp1.hw05.shell.commands;

import java.util.Arrays;
import java.util.List;
import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellIOException;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

/**
 * Symbol command implementation for MyShell. 
 */
public class SymbolShellCommand implements ShellCommand {

	private String commandName = "symbol";
	private List<String> commandDescription = Arrays.asList(
			"SYMBOL COMMAND - (usage: symbol <symbol_name> or symbol <symbol_name> <character>)",
			"If only one argument is given, prints the symbol for the wanted symbol name.",
			"When two arguments are given, changes the wanted symbol to the given value.\n"
		);
	
	/**
	 * Executes symbol shell command.
	 * 
	 * @throws ShellIOException if reading or writing through the environment is not possible
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		CommandArgumentParser parser = new CommandArgumentParser(arguments, false); 
		List<String> args = parser.getArguments();
		
		if (args.size() == 1) {
			switch(args.get(0)) {
				case "PROMPT" -> env.writeln(String.format("Symbol for PROMPT is '%c'", env.getPromptSymbol()));
				case "MULTILINE" -> env.writeln(String.format("Symbol for MULTILINE  is '%c'", env.getMultilineSymbol()));
				case "MORELINES" -> env.writeln(String.format("Symbol for MORELINES is '%c'", env.getMorelinesSymbol()));
				default -> env.writeln(String.format("%s symbol does not exist!", args.get(0)));
			}
		} else if (args.size() == 2) {
			if (args.get(1).length() != 1) {
				env.writeln("You must provide a valid symbol!");
				return ShellStatus.CONTINUE;
			}
			
			switch(args.get(0)) {
				case "PROMPT" -> {
					env.writeln(String.format("Symbol for PROMPT changed from '%c' to '%s'", env.getPromptSymbol(), args.get(1)));
					env.setPromptSymbol(args.get(1).charAt(0));
				}
				case "MULTILINE" -> {
					env.writeln(String.format("Symbol for MULTILINE changed from '%c' to '%s'", env.getMultilineSymbol(), args.get(1)));
					env.setMultilineSymbol(args.get(1).charAt(0));
				}
				case "MORELINES" -> {
					env.writeln(String.format("Symbol for MORELINES changed from '%c' to '%s'", env.getMorelinesSymbol(), args.get(1)));
					env.setMorelinesSymbol(args.get(1).charAt(0));
				}
				default -> env.writeln(String.format("%s symbol does not exist!", args.get(0)));
			}
			
		} else {
			env.writeln("Wrong symbol command argument input!");
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
