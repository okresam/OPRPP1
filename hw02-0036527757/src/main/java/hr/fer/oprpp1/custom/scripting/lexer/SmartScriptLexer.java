package hr.fer.oprpp1.custom.scripting.lexer;

/**
 * Implementation of a lexical analyzer for the SmartScriptParser.
 */
public class SmartScriptLexer {
	
	/**
	 * Array of all characters of the input text for the SmartScriptLexer.
	 */
	private char[] data;
	
	/**
	 * Current token, last token that was created by the SmartScriptLexer.
	 */
	private Token token;
	
	/**
	 * Index of the first unprocessed character.
	 */
	private int currentIndex;
	
	/**
	 * The state of the SmartScriptLexer.
	 */
	private SmartScriptLexerState state;
	
	/**
	 * Constructor for the SmartScriptLexer, sets the given input text and the state of the 
	 * SmartScriptLexer to BASIC.
	 * 
	 * @param text input text for the SmartScriptLexer
	 * @throws NullPointerException when the input text is a null reference
	 */
	public SmartScriptLexer(String text) {
		if (text == null) {
			throw new NullPointerException();
		}
		
		this.data = text.toCharArray();
		this.currentIndex = 0;
		this.state = SmartScriptLexerState.BASIC;
	}
	
	/**
	 * Generates the next token for the given input text to the SmartScriptLexer.
	 * 
	 * @return the next token of the input text
	 */
	public Token nextToken() {
		
		//when trying to get nextToken after EOF token
		if (this.currentIndex == data.length + 1) {
			throw new SmartScriptLexerException();
		}
		
		//got to the end of the input text
		if (this.currentIndex == data.length) {
			Token nTok = new Token(TokenType.EOF, null);
			this.token = nTok;

			this.currentIndex++;

			return nTok;
		}

		//check if input starts with a tag and immediately switch to tag reading mode
		if (data[currentIndex] == '{' && this.currentIndex + 1 < this.data.length - 1 
				&& data[currentIndex + 1] == '$') {
			
			this.setState(SmartScriptLexerState.TAG_TYPE_READING);
			this.currentIndex += 2;
			
			Token nTok = new Token(TokenType.OPEN_TAG, "{$"); 
			this.token = nTok;
			return nTok;
		}
		

		if (this.state == SmartScriptLexerState.BASIC) {
			//SmartScriptLexer in basic state
			
			String text = "";

			while (this.currentIndex < this.data.length) {
				
				//break and return text token if a open tag is found
				if (data[currentIndex] == '{' && this.currentIndex + 1 < this.data.length - 1 
						&& data[currentIndex + 1] == '$') {
					
					break;
				}

				//check for escaping
				if (data[currentIndex] == '\\' && (this.currentIndex + 1 == this.data.length
						|| (data[currentIndex + 1] != '{' && this.data[currentIndex + 1] != '\\'))) {

					throw new SmartScriptLexerException();
				}

				if (data[currentIndex] == '\\') {
					text += data[++currentIndex];
					currentIndex++;
				} else {
					text += data[currentIndex++];
				}

			}
			
			Token nTok = new Token(TokenType.TEXT, text);
			this.token = nTok;
			return nTok;
		
		} else if (this.state == SmartScriptLexerState.TAG_TYPE_READING){
			//SmartScriptLexer in tag type reading state
			
			//move past empty spaces if they exist
			this.movePastEmptyCharacters();

			this.setState(SmartScriptLexerState.TAG_CONTENT_READING);
			
			if (this.currentIndex + 2 < this.data.length
				&& Character.toUpperCase(data[currentIndex]) == 'F'
				&& Character.toUpperCase(data[currentIndex + 1]) == 'O'
				&& Character.toUpperCase(data[currentIndex + 2]) == 'R') {
				
				this.currentIndex += 3;
				
				Token nTok = new Token(TokenType.FOR_TAG, "FOR"); 
				this.token = nTok;
				return nTok;
				
			} else if (this.currentIndex + 2 < this.data.length
					   && Character.toUpperCase(data[currentIndex]) == 'E'
					   && Character.toUpperCase(data[currentIndex + 1]) == 'N'
					   && Character.toUpperCase(data[currentIndex + 2]) == 'D') {
				
				this.currentIndex += 3;
				
				Token nTok = new Token(TokenType.END_TAG, "END"); 
				this.token = nTok;
				return nTok;
				
			} else if (this.currentIndex < this.data.length
				&& data[currentIndex] == '=') {
				
				this.currentIndex++;
				
				Token nTok = new Token(TokenType.ECHO_TAG, "="); 
				this.token = nTok;
				return nTok;
				
			} else {
				throw new SmartScriptLexerException();
			}
			
		} else {
			//SmartScriptLexer in tag type content state

			this.movePastEmptyCharacters();
			
			//check for closed tag
			if (this.currentIndex + 1 < this.data.length
				&& data[currentIndex] == '$' && data[currentIndex + 1] == '}') {
					
				this.setState(SmartScriptLexerState.BASIC);
					
				this.currentIndex += 2;
					
				Token nTok = new Token(TokenType.CLOSED_TAG, "$}"); 
				this.token = nTok;
				return nTok;
			}
			
			
			//check for variable
			if (Character.isLetter(data[currentIndex])) {
				
				String strVar = "";
				
				while (this.currentIndex < this.data.length 
					   && (Character.isLetter(data[currentIndex]) 
						   || Character.isDigit(data[currentIndex])
						   || data[currentIndex] == '_')) {
				
					strVar += data[currentIndex++];
				}
				
				Token nTok = new Token(TokenType.VARIABLE, strVar);
				this.token = nTok;
				
				return nTok;
			}
			
			//check for function name
			
			if (this.currentIndex + 1 < this.data.length &&
				data[currentIndex] == '@' && Character.isLetter(data[currentIndex + 1])) {
				
				String strFun = "@";
				this.currentIndex++;
				strFun += data[currentIndex++];
				
				while (this.currentIndex < this.data.length &&
					   (Character.isLetter(data[currentIndex]) ||
					    Character.isDigit(data[currentIndex]) ||
					    data[currentIndex] == '_')) {
					
					strFun += data[currentIndex++];
				}
				
				Token nTok = new Token(TokenType.FUNCTION, strFun);
				this.token = nTok;
				
				return nTok;
			}
			
			//check for operators
			if (data[currentIndex] == '*' ||
				data[currentIndex] == '+' ||
				data[currentIndex] == '/' ||
				data[currentIndex] == '^') {
				
				Token nTok = new Token(TokenType.OPERATOR, data[currentIndex++]);
				this.token = nTok;
				
				return nTok;
			}
			
			if ((this.currentIndex + 1 < this.data.length &&
				data[currentIndex] == '-' && !Character.isDigit(data[currentIndex + 1])) ||
				this.currentIndex == (this.data.length - 1) && data[currentIndex] == '-') {
					
				Token nTok = new Token(TokenType.OPERATOR, data[currentIndex++]);
				this.token = nTok;
					
				return nTok;
			}
			
			//check for number
			if (data[currentIndex] == '-' || Character.isDigit(data[currentIndex])){
				
				String strNum;
				if (data[currentIndex] == '-') {
					strNum = "-";
					this.currentIndex++;
				} else {
					strNum = "";
				}
								
				boolean dotFound = false;
				
				while (this.currentIndex < this.data.length &&
					   Character.isDigit(data[currentIndex]) || (data[currentIndex] == '.' && !dotFound)) {
					
					if (data[currentIndex] == '.') {
						dotFound = true;
					}
					
					strNum += data[currentIndex++];
				}
				
				Token nTok;
				if (dotFound) {
					nTok = new Token(TokenType.DOUBLE, Double.parseDouble(strNum));
				} else {
					nTok = new Token(TokenType.INTEGER, Integer.parseInt(strNum));
				}
				
				this.token = nTok;
				return nTok;
			}
			
			//check for string
			if (data[currentIndex] == '"') {
				String str = "";
				this.currentIndex++;
				
				while (this.currentIndex < this.data.length) {
					
					if (data[currentIndex] == '\\' && (this.currentIndex + 1 == this.data.length
						|| (data[currentIndex + 1] != '\\' && this.data[currentIndex + 1] != '"'))) {
						
						throw new SmartScriptLexerException();
					}
					
					if (data[currentIndex] == '\\') {
						str += data[++currentIndex];
						currentIndex++;
					} else {
						str += data[currentIndex++];
					}
					
					if (data[currentIndex] == '"') {
						this.currentIndex++;
						break;
					}
				}
				
				Token nTok = new Token(TokenType.STRING, str);
				this.token = nTok;
				
				return nTok;
			}
			
			throw new SmartScriptLexerException();
		}
	}
	
	/**
	 * Private function for moving currentIndex past empty characters.
	 */
	private void movePastEmptyCharacters() {
		while (this.currentIndex < this.data.length && (data[currentIndex] == ' ' || data[currentIndex] == '\n'
				|| data[currentIndex] == '\t' || data[currentIndex] == '\r')) {
			currentIndex++;
		}
	}
	
	/**
	 * Getter for the current token of the SmartScriptLexer.
	 * 
	 * @return current token of the SmartScriptLexer
	 */
	public Token getToken() {
		return this.token;
	}
	
	/**
	 * Sets the state of the SmartScriptLexer to the given state.
	 * 
	 * @param state state to which the SmartScriptLexer will be set
	 */
	public void setState(SmartScriptLexerState state) {
		if (state == null) {
			throw new NullPointerException();
		}
		
		this.state = state;
	}
}
