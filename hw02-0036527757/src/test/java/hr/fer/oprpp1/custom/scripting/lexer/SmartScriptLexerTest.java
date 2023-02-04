package hr.fer.oprpp1.custom.scripting.lexer;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class SmartScriptLexerTest {
	
	@Test
	public void testNotNull() {
		SmartScriptLexer lexer = new SmartScriptLexer("");
		
		assertNotNull(lexer.nextToken());
	}
	
	@Test
	public void testNullInput() {
		assertThrows(NullPointerException.class, () -> new SmartScriptLexer(null));
	}
	
	@Test
	public void testEmpty() {
		SmartScriptLexer lexer = new SmartScriptLexer("");
		
		assertEquals(TokenType.EOF, lexer.nextToken().getType());
	}
	
	@Test
	public void testGetReturnsLastNext() {
		SmartScriptLexer lexer = new SmartScriptLexer("");
		
		Token token = lexer.nextToken();
		assertEquals(token, lexer.getToken());
		assertEquals(token, lexer.getToken());
	}
	
	@Test
	public void testRadAfterEOF() {
		SmartScriptLexer lexer = new SmartScriptLexer("");
		lexer.nextToken();
		assertThrows(SmartScriptLexerException.class, () -> lexer.nextToken());
	}
	
	@Test
	public void testOneTextToken() {
		SmartScriptLexer lexer = new SmartScriptLexer("Some text");
		
		assertEquals(TokenType.TEXT, lexer.nextToken().getType());
	}
}
