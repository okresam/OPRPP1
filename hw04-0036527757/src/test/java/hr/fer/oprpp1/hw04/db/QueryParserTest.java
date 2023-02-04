package hr.fer.oprpp1.hw04.db;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;

public class QueryParserTest {

	@Test
	public void testParserEmptyString1() {
		QueryParser qp = new QueryParser("");
		assertEquals(new ArrayList<ConditionalExpression>(), qp.getQuery());
	}
	
	@Test
	public void testParserEmptyString2() {
		QueryParser qp = new QueryParser("   \n    \t");
		assertEquals(new ArrayList<ConditionalExpression>(), qp.getQuery());
	}
	
	@Test
	public void testParserOnlyJmbagEquals() {
		QueryParser qp = new QueryParser("jmbag=\"0000000004\"");
		
		assertEquals(FieldValueGetters.JMBAG, qp.getQuery().get(0).getFieldGetter());
		assertEquals("0000000004", qp.getQuery().get(0).getStringLiteral());
		assertEquals(ComparisonOperators.EQUALS, qp.getQuery().get(0).getComparisonOperator());
	}
	
	@Test
	public void testParserOnlyJmbagEqualsIsDirectQuery() {
		QueryParser qp = new QueryParser("jmbag=\"0000000004\"");
		
		assertEquals(true, qp.isDirectQuery());
	}
	
	@Test
	public void testParserOnlyJmbagGreaterIsDirectQuery() {
		QueryParser qp = new QueryParser("jmbag>\"0000000004\"");
		
		assertEquals(false, qp.isDirectQuery());
	}
	
	@Test
	public void testParserJmbagEqualsAndFirstNameLessIsDirectQuery() {
		QueryParser qp = new QueryParser("jmbag>\"0000000004\" AND firstName<\"Bobo\"");
		
		assertEquals(false, qp.isDirectQuery());
	}
	
	@Test
	public void testParserOnlyJmbagEqualsGetQueriedJMBAG() {
		QueryParser qp = new QueryParser("jmbag=\"0000000004\"");
		
		assertEquals("0000000004", qp.getQueriedJMBAG());
	}
	
	@Test
	public void testParserOnlyJmbagGreaterGetQueriedJMBAG() {
		QueryParser qp = new QueryParser("jmbag>\"0000000004\"");
		
		assertThrows(IllegalStateException.class, () -> qp.getQueriedJMBAG());
	}
	
	@Test
	public void testParserJmbagEqualsAndFirstNameLessGetQueriedJMBAG() {
		QueryParser qp = new QueryParser("jmbag>\"0000000004\" AND firstName<\"Bobo\"");
		
		assertThrows(IllegalStateException.class, () -> qp.getQueriedJMBAG());
	}
	
	@Test
	public void testParserJmbagEqualsAndLastNameLike() {
		QueryParser qp = new QueryParser("jmbag=\"0000000003\" AND lastName LIKE \"B*\"");
		
		assertEquals(FieldValueGetters.JMBAG, qp.getQuery().get(0).getFieldGetter());
		assertEquals("0000000003", qp.getQuery().get(0).getStringLiteral());
		assertEquals(ComparisonOperators.EQUALS, qp.getQuery().get(0).getComparisonOperator());
		assertEquals(FieldValueGetters.LAST_NAME, qp.getQuery().get(1).getFieldGetter());
		assertEquals("B*", qp.getQuery().get(1).getStringLiteral());
		assertEquals(ComparisonOperators.LIKE, qp.getQuery().get(1).getComparisonOperator());
	}
	
	@Test
	public void testParserWrongInput1() {		
		assertThrows(QueryParserException.class, () -> new QueryParser("jmbag = \"0000000005 "));
	}
	
	@Test
	public void testParserWrongInput2() {		
		assertThrows(QueryParserException.class, () -> new QueryParser("prviIme = \"0000000005 "));
	}
	
	@Test
	public void testParserWrongInput3() {		
		assertThrows(QueryParserException.class, () -> new QueryParser(" \"Bos*\" <= lastName "));
	}
	
	@Test
	public void testParserMoreWildcardsInLike() {		
		assertThrows(QueryParserException.class, () -> new QueryParser("firstName LIKE \"B***\" "));
	}
}
