package hr.fer.oprpp1.hw05.shell.commands;

import java.util.ArrayList;
import java.util.List;

/**
 * Parser implementation for parsing input arguments for shell commands.
 */
public class CommandArgumentParser {

	/**
	 * List of all arguments of the input argument string.
	 */
	List<String> arguments;
	
	/**
	 * Constructor for CommandArgumentParser.
	 * 
	 * @param argumentString - input argument string to be parsed 
	 * @param containsFilePaths - boolean telling if the command can have file paths as input
	 */
	public CommandArgumentParser(String argumentString, boolean containsFilePaths) {
		arguments = new ArrayList<>();
		parse(argumentString.toCharArray(), containsFilePaths);
	}
	
	/**
	 * Getter for arguments.
	 * 
	 * @return arguments list
	 */
	public List<String> getArguments() {
		return arguments;
	}
	
	/**
	 * Internal function for CommandArgumentParser which does the actual parsing.
	 * 
	 * @param argumentString - input argument string as char array
	 * @param containsFilePaths - boolean telling if the command can have file paths as input
	 * @throws CommandArgumentParserException if the input argument string is invalid
	 */
	private void parse(char[] argumentString, boolean containsFilePaths) {
		int index = 0;
		String currArgument;
		
		while(index < argumentString.length) {
			index = movePastEmptyCharacters(index, argumentString);
			currArgument = "";
			
			if (containsFilePaths && argumentString[index] == '"') {
				index++;

				while (index < argumentString.length && argumentString[index] != '"') {
					if (argumentString[index] == '\\' && index + 1 < argumentString.length 
						&& (argumentString[index + 1] == '\\' || argumentString[index + 1] == '"')) {
						index++;
						currArgument += argumentString[index++];
					} else {
						currArgument += argumentString[index++];
					}
				}
				
				if (index >= argumentString.length && argumentString[index - 1] != '"') {
					throw new CommandArgumentParserException();
				}
				
				index++;
				
				if (index < argumentString.length && argumentString[index] != ' ') {
					throw new CommandArgumentParserException();
				}
				
				arguments.add(currArgument);
				continue;
			}
			
			while (index < argumentString.length && argumentString[index] != ' ') {
				currArgument += argumentString[index++];
			}
			arguments.add(currArgument);
		}
	}
	
	/**
	 * Function for moving the reading index past empty characters.
	 * 
	 * @param index - input reading index
	 * @param argumentString - argument string as char array
	 * @return index value past empty characters
	 */
	private int movePastEmptyCharacters(int index, char[] argumentString) {
		while (index < argumentString.length && (argumentString[index] == ' ' || argumentString[index] == '\n'
				|| argumentString[index] == '\t' || argumentString[index] == '\r')) {
			index++;
		}
		return index;
	}
}
