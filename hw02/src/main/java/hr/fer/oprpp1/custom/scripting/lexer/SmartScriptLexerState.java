package hr.fer.oprpp1.custom.scripting.lexer;

/**
 * States in which the SmartScriptLexor can be.
 */
public enum SmartScriptLexerState {
	
	/**
	 * Basic state of the SmartScriptLexor.
	 */
	BASIC,
	
	/**
	 * Tag type reading state of the SmartScriptLexor.
	 */
	TAG_TYPE_READING,
	
	/**
	 * Tag content reading state of the SmartScriptLexor.
	 */
	TAG_CONTENT_READING;
}
