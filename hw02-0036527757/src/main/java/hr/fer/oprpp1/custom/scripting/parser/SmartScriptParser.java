package hr.fer.oprpp1.custom.scripting.parser;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;
import hr.fer.oprpp1.custom.collections.ObjectStack;
import hr.fer.oprpp1.custom.scripting.elems.*;
import hr.fer.oprpp1.custom.scripting.lexer.*;
import hr.fer.oprpp1.custom.scripting.nodes.*;

/**
 * An implementation of a Parser for a specific structured document format.
 */
public class SmartScriptParser {
	
	/**
	 * DocumentNode of the given input text to the parser.
	 */
	private DocumentNode docNode;
	
	/**
	 * Instance of an ObjectStack used for parsing.
	 */
	private ObjectStack obStack;
	
	/**
	 * Constructor for the SmartScriptParser. Creates a new SmartScriptLexer instance,
	 * gives it the given input text and parses the whole input text.
	 * 
	 * @param text
	 */
	public SmartScriptParser(String text) {
		if (text == null) {
			throw new SmartScriptParserException();
		}
		
		this.docNode = new DocumentNode();
		this.obStack = new ObjectStack();
		this.obStack.push(this.docNode);
		
		SmartScriptLexer l = new SmartScriptLexer(text);
		
		this.parseInput(l);
	}
	
	/**
	 * Parses the whole input text given to the parser.
	 * 
	 * @param lexer SmartScriptLexer that will tokenize the input text for the parser
	 */
	private void parseInput(SmartScriptLexer lexer) {
		Token curToken;
		
		try {
			curToken = lexer.nextToken();
		} catch (Exception e) {
			throw new SmartScriptParserException();
		}
		
		while (curToken.getType() != TokenType.EOF) {
			try {
				if (curToken.getType() == TokenType.TEXT) {
					((Node)this.obStack.peek()).addChildNode(new TextNode((String) curToken.getValue()));
				} else if (curToken.getType() == TokenType.FOR_TAG) {
					curToken = lexer.nextToken();
					
					if (curToken.getType() != TokenType.VARIABLE) {
						throw new SmartScriptParserException();
					}
					
					ElementVariable eVar = new ElementVariable((String) curToken.getValue());
					
					curToken = lexer.nextToken();
					
					Element el1 = switch(curToken.getType()) {
						case VARIABLE -> new ElementVariable((String) curToken.getValue());
						case INTEGER -> new ElementConstantInteger((int) curToken.getValue());
						case DOUBLE -> new ElementConstantDouble((double) curToken.getValue());
						case STRING -> new ElementString((String) curToken.getValue());
						default -> throw new SmartScriptParserException();
					};
					
					curToken = lexer.nextToken();
					
					Element el2 = switch(curToken.getType()) {
						case VARIABLE -> new ElementVariable((String) curToken.getValue());
						case INTEGER -> new ElementConstantInteger((int) curToken.getValue());
						case DOUBLE -> new ElementConstantDouble((double) curToken.getValue());
						case STRING -> new ElementString((String) curToken.getValue());
						default -> throw new SmartScriptParserException();
					};
					
					curToken = lexer.nextToken();
					
					if (curToken.getType() != TokenType.CLOSED_TAG) {
						Element el3 = switch(curToken.getType()) {
							case VARIABLE -> new ElementVariable((String) curToken.getValue());
							case INTEGER -> new ElementConstantInteger((int) curToken.getValue());
							case DOUBLE -> new ElementConstantDouble((double) curToken.getValue());
							case STRING -> new ElementString((String) curToken.getValue());
							default -> throw new SmartScriptParserException();
						};
						
						curToken = lexer.nextToken();
						
						if (curToken.getType() != TokenType.CLOSED_TAG) {
							throw new SmartScriptParserException();
						} else {
							ForLoopNode fln = new ForLoopNode(eVar, el1, el2, el3);
							((Node)this.obStack.peek()).addChildNode(fln);
							this.obStack.push(fln);
						}
					} else {
						ForLoopNode fln = new ForLoopNode(eVar, el1, el2, null);
						((Node)this.obStack.peek()).addChildNode(fln);
						this.obStack.push(fln);
					}
					
				} else if (curToken.getType() == TokenType.ECHO_TAG) {
					ArrayIndexedCollection arr = new ArrayIndexedCollection();
					
					curToken = lexer.nextToken();
					while (curToken.getType() != TokenType.CLOSED_TAG) {
						switch(curToken.getType()) {
							case VARIABLE:
								arr.add(new ElementVariable((String) curToken.getValue()));
								break;
							case INTEGER:
								arr.add(new ElementConstantInteger((int) curToken.getValue()));
								break;
							case DOUBLE:
								arr.add(new ElementConstantDouble((double) curToken.getValue()));
								break;
							case STRING:
								arr.add(new ElementString((String) curToken.getValue()));
								break;
							case FUNCTION:
								arr.add(new ElementFunction((String) curToken.getValue()));
								break;
							case OPERATOR:
								arr.add(new ElementOperator(String.valueOf(curToken.getValue())));
								break;
						default:
							break;
						}
						
						curToken = lexer.nextToken();
					}
					
					Element[] elArr = new Element[arr.size()];
					
					for (int i = 0; i < elArr.length; i++) {
						elArr[i] = (Element)arr.get(i);
					}
					
					((Node)this.obStack.peek()).addChildNode(new EchoNode(elArr));
				} else if (curToken.getType() == TokenType.END_TAG) {
					this.obStack.pop();
					
					if (obStack.size() == 0) {
						throw new SmartScriptParserException();
					}
				}
				
				curToken = lexer.nextToken();
			} catch (Exception e) {
				throw new SmartScriptParserException();
			}
		}
	}
	
	/**
	 * DocumentNode getter.
	 * 
	 * @return DocumentNode of the text given to the parser.
	 */
	public DocumentNode getDocumentNode() {
		return this.docNode;
	}
}
