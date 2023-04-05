package hr.fer.oprpp1.hw04.db;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

public class QueryFilterTest {

	List<ConditionalExpression> l1;
	List<ConditionalExpression> l2;
	QueryFilter qf;
	
	@BeforeEach
	public void beforeeach() {
		l1 = new ArrayList<>();
		l2 = new ArrayList<>();
		
		l1.add(new ConditionalExpression(
			FieldValueGetters.LAST_NAME,
			"Bo*",
			ComparisonOperators.LIKE
		));
		
		l1.add(new ConditionalExpression(
			FieldValueGetters.FIRST_NAME,
			"Ana",
			ComparisonOperators.GREATER
		));
		
		l2.add(new ConditionalExpression(
			FieldValueGetters.JMBAG,
			"0000000009",
			ComparisonOperators.EQUALS
		));
			
		l2.add(new ConditionalExpression(
			FieldValueGetters.FIRST_NAME,
			"Benjamin",
			ComparisonOperators.GREATER
		));
		
		l2.add(new ConditionalExpression(
			FieldValueGetters.LAST_NAME,
			"Anić",
			ComparisonOperators.GREATER
		));
	}
	
	@Test
	public void testL1AcceptsStudentRecord() {
		qf = new QueryFilter(l1);
		assertEquals(true, qf.accepts(new StudentRecord("0000000008", "Bonić", "Matej", "4")));
	}
	
	@Test
	public void testL2AcceptsStudentRecord() {
		qf = new QueryFilter(l2);
		assertEquals(true, qf.accepts(new StudentRecord("0000000009", "Lončarić", "Milan", "2")));
	}
	
	@Test
	public void testL1DoesNotAcceptStudentRecord() {
		qf = new QueryFilter(l1);
		assertEquals(false, qf.accepts(new StudentRecord("0000000008", "Bonić", "Aba", "3")));
	}
	
	@Test
	public void testL2DoesNotAcceptStudentRecord() {
		qf = new QueryFilter(l2);
		assertEquals(false, qf.accepts(new StudentRecord("0000000111", "Lončarić", "Andrej", "5")));
	}
}
