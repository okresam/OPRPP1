package hr.fer.oprpp1.hw04.db;

import java.util.ArrayList;
import java.util.List;
import hr.fer.oprpp1.hw04.lexer.QueryLexer;
import hr.fer.oprpp1.hw04.lexer.Token;
import hr.fer.oprpp1.hw04.lexer.TokenType;

/**
 * Parser for StudentDatabase queries.
 */
public class QueryParser {
	
	/**
	 * List of all ConditionalExpressions of a query.
	 */
	private List<ConditionalExpression> queryExpressions;
	
	
	private boolean orderByPresent = false;
	private List<String> orderByAttributes;
	
	/**
	 * Constructor for the QueryParser. Initializes queryExpressions and parses the query with the help of 
	 * the QueryLexer.
	 * 
	 * @param query - query to be parsed
	 */
	public QueryParser(String query) {
		queryExpressions = new ArrayList<>();
		orderByAttributes = new ArrayList<>();
		parseQuery(new QueryLexer(query));
	}
	
	public boolean isOrderByPresent() {
		return this.orderByPresent;
	}
	
	public List<String> getOrderByAttributes() {
		return this.orderByAttributes;
	}
	
	/**
	 * Checks if the query is a direct query. (Only has a jmbag attribute with equals operator)
	 * 
	 * @return true if the query is a direct one, false otherwise
	 */
	public boolean isDirectQuery() {
		return queryExpressions.size() == 1 
			&& queryExpressions.get(0).getFieldGetter() ==  FieldValueGetters.JMBAG
			&& queryExpressions.get(0).getComparisonOperator() == ComparisonOperators.EQUALS;
	}
	
	/**
	 * Gets the queried jmbag if the query is a direct one.
	 * 
	 * @return the queried jmbag
	 * @throws IllegalStateException - when this function is called and the query was not a direct one
	 */
	public String getQueriedJMBAG() {
		if (!isDirectQuery()) {
			throw new IllegalStateException();
		}
		
		return queryExpressions.get(0).getStringLiteral();
	}
	
	/**
	 * Gets a list of all ConditionalExpressions of the query.
	 * 
	 * @return list of all ConditionalExpressions of the query.
	 */
	public List<ConditionalExpression> getQuery() {
		return this.queryExpressions;
	}
	
	/**
	 * Parses the query and fills the internal list of the parser with ConditinalExpressions of the given query.
	 * 
	 * @param ql - QueryLexer with the query given as input
	 * @throws QueryParserException - when a invalid query is given
	 */
	private	void parseQuery(QueryLexer ql) {
		Token curToken;
		Token prevToken = null;
		
		IFieldValueGetter tempVG = null;
		String tempSL = null;
		IComparisonOperator tempCO = null;
		
		try {
			curToken = ql.nextToken();
		} catch (Exception e) {
			throw new QueryParserException();
		}
		
		while (curToken.getType() != TokenType.EOF) {
			switch(curToken.getType()) {
				case ATTRIBUTE:
					if (prevToken != null && (prevToken.getType() == TokenType.OPERATOR 
						|| prevToken.getType() == TokenType.ATTRIBUTE 
						|| prevToken.getType() == TokenType.STRING_LITERAL)) {
						
						throw new QueryParserException();
					}
					
					tempVG = switch(String.valueOf(curToken.getValue())) {
						case "firstName" -> FieldValueGetters.FIRST_NAME; 
						case "lastName" -> FieldValueGetters.LAST_NAME;
						case "jmbag" -> FieldValueGetters.JMBAG;
						default -> throw new QueryParserException();
					};
					
					break;
				case OPERATOR:
					if (prevToken == null || prevToken.getType() != TokenType.ATTRIBUTE) {
						throw new QueryParserException();
					}
					
					tempCO = switch(String.valueOf(curToken.getValue())) {
						case "<" -> ComparisonOperators.LESS;
						case "<=" -> ComparisonOperators.LESS_OR_EQUALS;
						case ">" -> ComparisonOperators.GREATER;
						case ">=" -> ComparisonOperators.GREATER_OR_EQUALS;
						case "=" -> ComparisonOperators.EQUALS;
						case "!=" -> ComparisonOperators.NOT_EQUALS;
						case "LIKE" -> ComparisonOperators.LIKE;
						default -> throw new QueryParserException();
					};
					
					break;
				case STRING_LITERAL:
					if (prevToken == null || prevToken.getType() != TokenType.OPERATOR) {
						throw new QueryParserException();
					}
					
					String strLiteral = String.valueOf(curToken.getValue());
					
					if (prevToken.getValue().equals("LIKE")) {
						int countWildcard = 0;
						for (char c : strLiteral.toCharArray()) {
							if (c == '*') {
								countWildcard++;
							}
						}
						
						if (countWildcard > 1) {
							throw new QueryParserException();
						}
					}
					
					tempSL = String.valueOf(strLiteral);
					
					break;
				case AND:
					if (prevToken == null || prevToken.getType() != TokenType.STRING_LITERAL) {
						throw new QueryParserException();
					}
					break;
				case ORDER_BY:
					if (prevToken == null || prevToken.getType() != TokenType.STRING_LITERAL) {
						throw new QueryParserException();
					}
					orderByPresent = true;
					
					try {
						curToken = ql.nextToken();
					} catch (Exception e) {
						throw new QueryParserException();
					}
					
					if (curToken.getType() == TokenType.EOF) {
						throw new QueryParserException();
					}
					
					while (curToken.getType() != TokenType.EOF) { 
						if (curToken.getType() != TokenType.ATTRIBUTE) {
							throw new QueryParserException();
						}
						
						String attributeName = String.valueOf(curToken.getValue());
						
						orderByAttributes.add(attributeName);
						
						try {
							curToken = ql.nextToken();
						} catch (Exception e) {
							throw new QueryParserException();
						}
					}
					break;
				default: throw new QueryParserException();
			}
			
			if (tempVG != null && tempSL != null && tempCO != null) {
				queryExpressions.add(new ConditionalExpression(tempVG, tempSL,tempCO));
				
				tempVG = null;
				tempSL = null;
				tempCO = null;
			}
			
			prevToken = curToken;
			try {
				if (curToken.getType() != TokenType.EOF && !orderByPresent) {
					curToken = ql.nextToken();
				}
			} catch (Exception e) {
				throw new QueryParserException();
			}
		}
		
		if (tempVG != null || tempSL != null || tempCO != null) {
			throw new QueryParserException();
		}
		
	}
	
}
