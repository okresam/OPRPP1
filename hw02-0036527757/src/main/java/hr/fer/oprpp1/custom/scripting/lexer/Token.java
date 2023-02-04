package hr.fer.oprpp1.custom.scripting.lexer;

/**
 * A class which represents a Token, a lexical unit which 
 * groups characters from the input text.
 */
public class Token {
	
	/**
	 * Stores the type of the token.
	 */
	TokenType type;
	
	/**
	 * Stores the value of the token.
	 */
	Object value;
	
	/**
	 * Token constructor that sets the type and value of the token to the given values.
	 * 
	 * @param type given type of the token that will be initialized
	 * @param value given value of the token that will be initialized
	 */
	public Token(TokenType type, Object value) {
		this.type = type;
		this.value = value;
	}

	/**
	 * Token value getter.
	 * 
	 * @return value of the token
	 */
	public Object getValue() {
		return this.value;
	}

	/**
	 * Token type getter.
	 * 
	 * @return type of the token
	 */
	public TokenType getType() {
		return this.type;
	}
}
