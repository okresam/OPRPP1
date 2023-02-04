package hr.fer.oprpp1.hw04.lexer;

/**
 * Implementation of a lexical analyzer for the QueryParser.
 * Generates tokens for the given query.
 */
public class QueryLexer {

	/**
	 * Array of all characters of the input text for the QueryLexer.
	 */
	private char[] data;
	
	/**
	 * Current token, last token that was created by the QueryLexer.
	 */
	private Token token;
	
	/**
	 * Index of the first unprocessed character.
	 */
	private int currentIndex;
	
	/**
	 * Constructor for the QueryLexer, sets the given input text.
	 * 
	 * @param text - input text for the QueryLexer
	 * @throws NullPointerException when the input text is a null reference
	 */
	public QueryLexer(String text) {
		if (text == null) {
			throw new NullPointerException();
		}
		
		this.data = text.toCharArray();
		this.currentIndex = 0;
	}
	
	/**
	 * Generates the next token for the given input text to the QueryLexer.
	 * 
	 * @return the next token of the input text
	 */
	public Token nextToken() {
		
		//moving past empty spaces and tabs
		movePastEmptyCharacters();
		
		//when trying to get nextToken after EOF token
		if (this.currentIndex == data.length + 1) {
			throw new QueryLexerException();
		}
		
		//got to the end of the input text
		if (this.currentIndex == data.length) {
			Token nTok = new Token(TokenType.EOF, null);
			this.token = nTok;

			this.currentIndex++;

			return nTok;
		}

		String temp = "";

		if (Character.isLetter(data[currentIndex])) {
			while (this.currentIndex < this.data.length && Character.isLetter(data[currentIndex])) {
				temp += data[currentIndex++];
			}
			
			Token nTok;
			if (temp.equals("orderby")) {
				nTok = new Token(TokenType.ORDER_BY, temp);
			}else if (temp.toUpperCase().equals("AND")) {
				nTok = new Token(TokenType.AND, temp);
			} else if (temp.equals("LIKE")){
				nTok = new Token(TokenType.OPERATOR, temp);
			} else {
				nTok = new Token(TokenType.ATTRIBUTE, temp);
			}
			
			this.token = nTok;
			return nTok;
		}
		
		if (data[currentIndex] == '"') {
			this.currentIndex++;
			
			while (this.currentIndex < this.data.length && data[currentIndex] != '"') {
				temp += data[currentIndex++];
			}
			
			if (data[currentIndex] == '"') {
				this.currentIndex++;
			}
			
			Token nTok = new Token(TokenType.STRING_LITERAL, temp);
			this.token = nTok;
			return nTok;
		}

		if (data[currentIndex] == '<' || 
			data[currentIndex] == '>' || 
			data[currentIndex] == '=' ||
			data[currentIndex] == '!') {
			
			temp += data[currentIndex++];
			
			if (this.currentIndex < this.data.length && temp != "=" && data[currentIndex] == '=') {
				temp += data[currentIndex++];
			}
			
			Token nTok = new Token(TokenType.OPERATOR, temp);
			this.token = nTok;
			return nTok;
		}
		
		throw new QueryLexerException();
	}
	
	/**
	 * Function for moving currentIndex past empty characters.
	 */
	private void movePastEmptyCharacters() {
		while (this.currentIndex < this.data.length && (data[currentIndex] == ' ' || data[currentIndex] == '\n'
				|| data[currentIndex] == '\t' || data[currentIndex] == '\r' || data[currentIndex] == ',')) {
			currentIndex++;
		}
	}
	
	/**
	 * Getter for the current token of the QueryLexer.
	 * 
	 * @return current token of the QueryLexer
	 */
	public Token getToken() {
		return this.token;
	}
	
}
