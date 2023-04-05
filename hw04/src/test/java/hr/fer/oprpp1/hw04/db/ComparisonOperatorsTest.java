package hr.fer.oprpp1.hw04.db;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class ComparisonOperatorsTest {

	@Test
	public void testLessWhenItIsLess() {
		IComparisonOperator oper = ComparisonOperators.LESS;
		assertEquals(true, oper.satisfied("Ana", "Jasna"));
	}
	
	@Test
	public void testLessWhenItIsNotLess() {
		IComparisonOperator oper = ComparisonOperators.LESS;
		assertEquals(false, oper.satisfied("Jasna", "Ana"));
	}
	
	@Test
	public void testLessOrEqualsWhenItIsLess() {
		IComparisonOperator oper = ComparisonOperators.LESS_OR_EQUALS;
		assertEquals(true, oper.satisfied("Ana", "Jasna"));
	}
	
	@Test
	public void testLessOrEqualsWhenItIsEqual() {
		IComparisonOperator oper = ComparisonOperators.LESS_OR_EQUALS;
		assertEquals(true, oper.satisfied("Ana", "Ana"));
	}
	
	@Test
	public void testLessOrEqualsWhenItIsNotLessOrEqual() {
		IComparisonOperator oper = ComparisonOperators.LESS_OR_EQUALS;
		assertEquals(false, oper.satisfied("Jasna", "Ana"));
	}
	
	@Test
	public void testGreaterWhenItIsGreater() {
		IComparisonOperator oper = ComparisonOperators.GREATER;
		assertEquals(true, oper.satisfied("Zozo", "Mihael"));
	}
	
	@Test
	public void testGreaterWhenItIsNotGreater() {
		IComparisonOperator oper = ComparisonOperators.GREATER;
		assertEquals(false, oper.satisfied("Ana", "Koko"));
	}
	
	@Test
	public void testGreaterOrEqualsWhenItIsGreater() {
		IComparisonOperator oper = ComparisonOperators.GREATER_OR_EQUALS;
		assertEquals(true, oper.satisfied("Kevin", "Benjamin"));
	}
	
	@Test
	public void testGreaterOrEqualsWhenItIsEqual() {
		IComparisonOperator oper = ComparisonOperators.GREATER_OR_EQUALS;
		assertEquals(true, oper.satisfied("David", "David"));
	}
	
	@Test
	public void testGreaterOrEqualsWhenItIsNotGreaterOrEqual() {
		IComparisonOperator oper = ComparisonOperators.GREATER_OR_EQUALS;
		assertEquals(false, oper.satisfied("Bongo", "Leon"));
	}
	
	@Test
	public void testEqualsWhenItIsEqual() {
		IComparisonOperator oper = ComparisonOperators.EQUALS;
		assertEquals(true, oper.satisfied("Bongo", "Bongo"));
	}
	
	@Test
	public void testEqualsWhenItIsNotEqual() {
		IComparisonOperator oper = ComparisonOperators.EQUALS;
		assertEquals(false, oper.satisfied("Bongo", "Leon"));
	}
	
	@Test
	public void testNotEqualsWhenItIsEqual() {
		IComparisonOperator oper = ComparisonOperators.NOT_EQUALS;
		assertEquals(false, oper.satisfied("Bongo", "Bongo"));
	}
	
	@Test
	public void testNotEqualsWhenItIsNotEqual() {
		IComparisonOperator oper = ComparisonOperators.NOT_EQUALS;
		assertEquals(true, oper.satisfied("Bongo", "Leon"));
	}
	
	@Test
	public void testLikeWildcardAtBeginningMatching() {
		IComparisonOperator oper = ComparisonOperators.LIKE;
		assertEquals(true, oper.satisfied("Bongo", "*ongo"));
	}
	
	@Test
	public void testLikeWildcardAtEndMatching() {
		IComparisonOperator oper = ComparisonOperators.LIKE;
		assertEquals(true, oper.satisfied("Bongo", "Bo*"));
	}
	
	@Test
	public void testLikeWildcardAtEndNotMatching() {
		IComparisonOperator oper = ComparisonOperators.LIKE;
		assertEquals(false, oper.satisfied("Zagreb", "Aba*"));
	}
	
	@Test
	public void testLikeWildcardInTheMiddleNotMatching() {
		IComparisonOperator oper = ComparisonOperators.LIKE;
		assertEquals(false, oper.satisfied("AAA", "AA*AA"));
	}
	
	@Test
	public void testLikeWildCardInTheMiddleMatching1() {
		IComparisonOperator oper = ComparisonOperators.LIKE;
		assertEquals(true, oper.satisfied("AAAA", "AA*AA"));
	}
	
	@Test
	public void testLikeWildCardInTheMiddleMatching2() {
		IComparisonOperator oper = ComparisonOperators.LIKE;
		assertEquals(true, oper.satisfied("Benjamin", "Ben*in"));
	}
	
	@Test
	public void testLikeNoWildcardEqual() {
		IComparisonOperator oper = ComparisonOperators.LIKE;
		assertEquals(true, oper.satisfied("Dodo", "Dodo"));
	}
	
	@Test
	public void testLikeNoWildcardNotEqual() {
		IComparisonOperator oper = ComparisonOperators.LIKE;
		assertEquals(false, oper.satisfied("Dodododo", "Dodo"));
	}
}
