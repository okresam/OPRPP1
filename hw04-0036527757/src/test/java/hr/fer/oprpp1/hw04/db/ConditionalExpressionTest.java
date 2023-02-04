package hr.fer.oprpp1.hw04.db;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ConditionalExpressionTest {

	StudentRecord sr;
	
	@BeforeEach
	public void beforeeach() {
		sr = new StudentRecord("0000000043", "Bosnić", "Krešimir", "4");
	}
	
	@Test
	public void TestRecordSatisfies1() {
		ConditionalExpression expr = new ConditionalExpression(
			FieldValueGetters.LAST_NAME,
			"Bos*",
			ComparisonOperators.LIKE
		);
		
		boolean recordSatisfies = expr.getComparisonOperator().satisfied(
			expr.getFieldGetter().get(sr),
			expr.getStringLiteral()
		);
		
		assertEquals(true, recordSatisfies);
	}
	
	@Test
	public void TestRecordSatisfies2() {
		ConditionalExpression expr = new ConditionalExpression(
			FieldValueGetters.JMBAG,
			"0000000043",
			ComparisonOperators.EQUALS
		);
		
		boolean recordSatisfies = expr.getComparisonOperator().satisfied(
			expr.getFieldGetter().get(sr),
			expr.getStringLiteral()
		);
		
		assertEquals(true, recordSatisfies);
	}
	
	@Test
	public void TestRecordDoesNotSatisfie1() {
		ConditionalExpression expr = new ConditionalExpression(
			FieldValueGetters.FIRST_NAME,
			"Benjamin",
			ComparisonOperators.LESS
		);
		
		boolean recordSatisfies = expr.getComparisonOperator().satisfied(
			expr.getFieldGetter().get(sr),
			expr.getStringLiteral()
		);
		
		assertEquals(false, recordSatisfies);
	}
	
	@Test
	public void TestRecordDoesNotSatisfie2() {
		ConditionalExpression expr = new ConditionalExpression(
			FieldValueGetters.LAST_NAME,
			"Kosić",
			ComparisonOperators.GREATER
		);
		
		boolean recordSatisfies = expr.getComparisonOperator().satisfied(
			expr.getFieldGetter().get(sr),
			expr.getStringLiteral()
		);
		
		assertEquals(false, recordSatisfies);
	}
}
