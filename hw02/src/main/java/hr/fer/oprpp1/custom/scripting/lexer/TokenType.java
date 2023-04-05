package hr.fer.oprpp1.custom.scripting.lexer;

/**
 * Types of Tokens that the SmartScriptLexer can generate.
 */
public enum TokenType {
	
	/**
	 * Text token.
	 */
	TEXT,
	
	/**
	 * Open tag token.
	 */
	OPEN_TAG,
	
	/**
	 * Closed tag token.
	 */
	CLOSED_TAG,
	
	/**
	 * For tag token.
	 */
	FOR_TAG,
	
	/**
	 * End tag token.
	 */
	END_TAG,
	
	/**
	 * Echo tag token.
	 */
	ECHO_TAG,
	
	/**
	 * Variable token.
	 */
	VARIABLE,
	
	/**
	 * Integer token.
	 */
	INTEGER,
	
	/**
	 * Double token.
	 */
	DOUBLE,
	
	/**
	 * String token.
	 */
	STRING,
	
	/**
	 * Function token.
	 */
	FUNCTION,
	
	/**
	 * Operator token.
	 */
	OPERATOR,
	
	/**
	 * End of file token.
	 */
	EOF;
	
}
