package hr.fer.oprpp1.custom.scripting.parser;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import hr.fer.oprpp1.custom.scripting.nodes.DocumentNode;

public class SmartScriptParserTest {

	@Test
	public void testNotNull() {
		SmartScriptParser lexer = new SmartScriptParser("");
		
		assertEquals(new DocumentNode(), lexer.getDocumentNode());
	}
	
	@Test
	public void testNullInput() {
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(null));
	}
	
	@Test
	public void testParseDocumentNodeAgainOneTextNode() {
		SmartScriptParser parser = new SmartScriptParser("Neki text");
		DocumentNode dn = parser.getDocumentNode();
		
		String originalDocumentBody = dn.toString();
		SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
		DocumentNode dn2 = parser2.getDocumentNode();
		
		assertEquals(dn, dn2);
	}
	
	@Test
	public void testParseDocumentNodeAgainOneTextNodeAndOneTag() {
		SmartScriptParser parser = new SmartScriptParser("Neki text {$= @func var23 $}");
		DocumentNode dn = parser.getDocumentNode();
		
		String originalDocumentBody = dn.toString();
		SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
		DocumentNode dn2 = parser2.getDocumentNode();
		
		assertEquals(dn, dn2);
	}
}
