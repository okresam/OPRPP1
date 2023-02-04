package hr.fer.oprpp1.custom.scripting.parser;

import hr.fer.oprpp1.custom.scripting.nodes.DocumentNode;

public class ParserTesting {
	public static void main(String[] args) {
		SmartScriptParser parser = new SmartScriptParser("Neki text {$    FOR   var  1   2 $} vise teksta {$= @func n2eka_vari3jabla   \"stringtest\"$} {$END$}");
		DocumentNode dn = parser.getDocumentNode();
		
		String originalDocumentBody = dn.toString();
		SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
		DocumentNode dn2 = parser2.getDocumentNode();
		
		System.out.println(dn);
		System.out.println(dn2);
		
		System.out.println(dn.equals(dn2));
	}
}
