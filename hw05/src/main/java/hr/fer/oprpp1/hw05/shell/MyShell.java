package hr.fer.oprpp1.hw05.shell;

import java.util.Collections;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

import hr.fer.oprpp1.hw05.shell.commands.*;

/**
 * MyShell v 1.0
 */
public class MyShell {

	public static void main(String[] args) {
		
		SortedMap<String, ShellCommand> commands = new TreeMap<>();
		commands.put("cat", new CatShellCommand());
		commands.put("charsets", new CharsetsShellCommand());
		commands.put("copy", new CopyShellCommand());
		commands.put("exit", new ExitShellCommand());
		commands.put("help", new HelpShellCommand());
		commands.put("hexdump", new HexdumpShellCommand());
		commands.put("ls", new LsShellCommand());
		commands.put("mkdir", new MkdirShellCommand());
		commands.put("symbol", new SymbolShellCommand());
		commands.put("tree", new TreeShellCommand());
		
		ShellStatus status = ShellStatus.CONTINUE;
		Scanner sc = new Scanner(System.in);
		
		Environment environment = new Environment(){

			private Character promptSymbol = '>';
			private Character multilineSymbol = '|';
			private Character morelinesSymbol = '\\';
			
			@Override
			public String readLine() throws ShellIOException {
				return sc.nextLine();
			}

			@Override
			public void write(String text) throws ShellIOException {
				System.out.printf("%s", text);
			}

			@Override
			public void writeln(String text) throws ShellIOException {
				System.out.println(text);
			}

			@Override
			public SortedMap<String, ShellCommand> commands() {
				return Collections.unmodifiableSortedMap(commands);
			}

			@Override
			public Character getMultilineSymbol() {
				return multilineSymbol;
			}

			@Override
			public void setMultilineSymbol(Character symbol) {
				this.multilineSymbol = symbol;
			}

			@Override
			public Character getPromptSymbol() {
				return promptSymbol;
			}

			@Override
			public void setPromptSymbol(Character symbol) {
				this.promptSymbol = symbol;
			}

			@Override
			public Character getMorelinesSymbol() {
				return morelinesSymbol;
			}

			@Override
			public void setMorelinesSymbol(Character symbol) {
				this.morelinesSymbol = symbol;
			}
			
		};
		
		try {
			environment.writeln("Welcome to MyShell v 1.0");
		} catch(ShellIOException e) {
			status = ShellStatus.TERMINATE;
		}
		
		while (status == ShellStatus.CONTINUE) {
			try {
				environment.write(environment.getPromptSymbol() + " ");
				String line = environment.readLine();
				
				while (line.length() > 0 
					   && ((Character)line.charAt(line.length() - 1)).equals(environment.getMorelinesSymbol())) {
					environment.write(environment.getMultilineSymbol() + " ");
					line = line.substring(0, line.length() - 1);
					line += environment.readLine();
				}
				
				String commandName;
				String arguments;
				if (line.indexOf(" ") != -1) {
					commandName = line.substring(0, line.indexOf(" "));
					arguments = line.substring(line.indexOf(" ") + 1);
				} else {
					commandName = line;
					arguments = "";
				}
				
				ShellCommand command = commands.get(commandName);
				
				if (command == null) {
					environment.writeln("Command does not exist! Please try again!");
				} else {
					status = command.executeCommand(environment, arguments);
				}
			} catch(ShellIOException e) {
				status = ShellStatus.TERMINATE;
			}
			
		}
		
		sc.close();
	}

}
