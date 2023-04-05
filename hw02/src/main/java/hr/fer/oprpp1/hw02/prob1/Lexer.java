package hr.fer.oprpp1.hw02.prob1;

/**
 * Implementation of a simple lexical analyzer.
 */
public class Lexer {
	
	/**
	 * Array of all characters of the input text for the Lexer.
	 */
	private char[] data;
	
	/**
	 * Current token, last token that was created by the Lexer.
	 */
	private Token token;
	
	/**
	 * Index of the first unprocessed character.
	 */
	private int currentIndex;
	
	/**
	 * The state of the Lexer.
	 */
	private LexerState state;
	
	/**
	 * Constructor for the Lexer, sets the given input text and the state of the Lexer to BASIC.
	 * 
	 * @param text input text for the Lexer
	 * @throws NullPointerException when the input text is a null reference
	 */
	public Lexer(String text) {
		if (text == null) {
			throw new NullPointerException();
		}
		
		this.data = text.toCharArray();
		this.currentIndex = 0;
		this.state = LexerState.BASIC;
	}
	
	/**
	 * Generates the next token for the given input text to the Lexer.
	 * 
	 * @return the next token of the input text
	 */
	public Token nextToken() {
		
		//nextToken called after it got to the end of the input text
		if (this.currentIndex == data.length + 1) {
			throw new LexerException();
		}
		
		//go past ignored characters
		while (this.currentIndex < this.data.length && (data[currentIndex] == ' ' || data[currentIndex] == '\n'
				|| data[currentIndex] == '\t' || data[currentIndex] == '\r')) {
			currentIndex++;
		}
		
		//check if the index got past the whole array
		if (this.currentIndex == data.length) {
			Token nTok = new Token(TokenType.EOF, null);
			this.token = nTok;

			this.currentIndex++;

			return nTok;
		}
		
		if (this.state == LexerState.BASIC) {

			//check for a word
			String strWord = "";

			while (this.currentIndex < this.data.length
					&& (Character.isLetter(data[currentIndex]) || data[currentIndex] == '\\')) {

				if (data[currentIndex] == '\\' && (this.currentIndex + 1 == this.data.length
						|| (!Character.isDigit(this.data[currentIndex + 1]) && this.data[currentIndex + 1] != '\\'))) {

					throw new LexerException();
				}

				if (data[currentIndex] == '\\') {
					strWord += data[++currentIndex];
					currentIndex++;
				} else {
					strWord += data[currentIndex++];
				}

			}

			if (strWord != "") {
				Token nTok = new Token(TokenType.WORD, strWord);
				this.token = nTok;
				
				return nTok;
			}

			//check for a number
			String strNumber = "";

			while (this.currentIndex < this.data.length && Character.isDigit(data[currentIndex])) {
				strNumber += data[currentIndex++];
			}

			if (strNumber != "") {
				try {
					long nmb = Long.parseLong(strNumber);
					
					Token nTok = new Token(TokenType.NUMBER, nmb);
					this.token = nTok;
					
					return nTok;
				} catch (Exception e) {
					throw new LexerException();
				}
			}
		
			
			//if the code execution got down here, it must be a symbol
			
			if (data[currentIndex] == '#') {
				this.setState(LexerState.EXTENDED);
			}
			
			Token nTok = new Token(TokenType.SYMBOL, data[currentIndex++]);
			this.token = nTok;
			
			return nTok;
		
		} else {
			
			if (data[currentIndex] == '#') {
				this.setState(LexerState.BASIC);
				
				Token nTok = new Token(TokenType.SYMBOL, data[currentIndex++]);
				this.token = nTok;
				
				return nTok;
			}
			
			String str = "";
			
			while (currentIndex < data.length) {
				if (data[currentIndex] == ' ' || data[currentIndex] == '\n'
					|| data[currentIndex] == '\t' || data[currentIndex] == '\r'
					|| data[currentIndex] == '#') {
					
					break;
				}
				str += data[currentIndex++];
			}
			
			Token nTok = new Token(TokenType.WORD, str);
			this.token = nTok;
			
			return nTok;
		}
		
	}
	
	/**
	 * Getter for the current token of the Lexer.
	 * 
	 * @return current token of the Lexer
	 */
	public Token getToken() {
		return this.token;
	}
	
	/**
	 * Sets the state of the Lexer to the given state.
	 * 
	 * @param state state to which the Lexer will be set
	 */
	public void setState(LexerState state) {
		if (state == null) {
			throw new NullPointerException();
		}
		
		this.state = state;
	}
}
