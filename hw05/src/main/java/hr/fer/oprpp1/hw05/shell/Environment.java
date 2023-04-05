package hr.fer.oprpp1.hw05.shell;

import java.util.SortedMap;

/**
 * Environment of MyShell through which it communicates with the user.
 */
public interface Environment {

	/**
	 * Reads next input line to MyShell.
	 * 
	 * @return next input line string
	 * @throws ShellIOException - when next input line could not be read
	 */
	String readLine() throws ShellIOException;
	
	/**
	 * Writes to the MyShell.
	 * 
	 * @param text - text that will be written to MyShell.
	 * @throws ShellIOException - when the text could not be written
	 */
	void write(String text) throws ShellIOException;
	
	/**
	 * Writes to the MyShell and adds a new line.
	 * 
	 * @param text - text that will be written to MyShell.
	 * @throws ShellIOException - when the text could not be written
	 */
	void writeln(String text) throws ShellIOException;
	
	/**
	 * Gets all commands available in MyShell.
	 * 
	 * @return SortedMap of all commands
	 */
	SortedMap<String, ShellCommand> commands();
	
	/**
	 * Gets the multiline symbol of MyShell.
	 * 
	 * @return multine symbol of MyShell
	 */
	Character getMultilineSymbol();
	
	/**
	 * Sets the multiline symbol of MyShell to the given symbol.
	 * 
	 * @param symbol - symbol that will be the new multiline symbol
	 */
	void setMultilineSymbol(Character symbol);
	
	/**
	 * Gets the prompt symbol of MyShell.
	 * 
	 * @return prompt symbol of MyShell
	 */
	Character getPromptSymbol();
	
	/**
	 * Sets the prompt symbol of MyShell to the given symbol.
	 * 
	 * @param symbol - symbol that will be the new prompt symbol
	 */
	void setPromptSymbol(Character symbol);
	
	/**
	 * Gets the morelines symbol of MyShell.
	 * 
	 * @return morelines symbol of MyShell
	 */
	Character getMorelinesSymbol();
	
	/**
	 * Sets the morelines symbol of MyShell to the given symbol.
	 * 
	 * @param symbol - symbol that will be the new morelines symbol
	 */
	void setMorelinesSymbol(Character symbol);
}
