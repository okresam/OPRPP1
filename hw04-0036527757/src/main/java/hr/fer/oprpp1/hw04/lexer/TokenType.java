package hr.fer.oprpp1.hw04.lexer;

/**
 * Types of Tokens that the QueryLexer can generate.
 */
public enum TokenType {
	
	/**
	 * Attribute token.
	 */
	ATTRIBUTE,
	
	/**
	 * Operator token.
	 */
	OPERATOR,
	
	/**
	 * String literal token.
	 */
	STRING_LITERAL,
	
	/**
	 * And token.
	 */
	AND,
	
	/**
	 * OrderBy token.
	 */
	ORDER_BY,
	
	/**
	 * End of file token.
	 */
	EOF;
	
}
