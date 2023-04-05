package hr.fer.oprpp1.hw02.prob1;

/**
 * Types of Tokens that the Lexer can generate.
 */
public enum TokenType {
	
	/**
	 * End of file token.
	 */
	EOF,
	
	/**
	 * Word token.
	 */
	WORD,
	
	/**
	 * Number token.
	 */
	NUMBER,
	
	/**
	 * Symbol token.
	 */
	SYMBOL;
}
