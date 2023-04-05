package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ArrayIndexedCollectionTest {
	
	ArrayIndexedCollection a;
	
	@BeforeEach
	public void beforeeach() {
		a = new ArrayIndexedCollection();
		a.add("Hello");
		a.add(25);
		a.add(133);
	}
	
	@Test
	public void testConstructorInitialCapacityLessThanOne() {
		assertThrows(IllegalArgumentException.class, () -> new ArrayIndexedCollection(0));
	}
	
	@Test
	public void testConstructorOtherIsNull() {
		assertThrows(NullPointerException.class, () -> new ArrayIndexedCollection(null));
	}
	
	@Test
	public void testConstructorCopiesOtherCollectionToThisNewArray() {
		ArrayIndexedCollection newArr = new ArrayIndexedCollection(a);
		assertArrayEquals(a.toArray(), newArr.toArray());
	}
	
	@Test
	public void testAddNullToArray() {
		assertThrows(NullPointerException.class, () -> a.add(null));
	}
	
	@Test
	public void testGetFromArrayWithInvalidIndex() {
		assertThrows(IndexOutOfBoundsException.class, () -> a.get(-2));
	}
	
	@Test
	public void testGetFromArrayWithValidIndex() {
		assertEquals(25, a.get(1));
	}
	
	@Test
	public void testClearArray() {
		a.clear();
		assertEquals(0, a.size());
	}
	
	@Test
	public void testInsertIntoArrayWithInvalidPosition() {
		assertThrows(IndexOutOfBoundsException.class, () -> a.insert(5, -3));
	}
	
	@Test
	public void testInsertNullIntoArrayOnValidPosition() {
		assertThrows(NullPointerException.class, () -> a.insert(null, 1));
	}
	
	@Test
	public void testInsertIntoArrayWithValidPosition() {
		Object[] expected = new Object[4];
		expected[0] = "Hello";
		expected[1] = "new string";
		expected[2] = 25;
		expected[3] = 133;
		
		String s = "new string";
		a.insert(s, 1);
		
		assertArrayEquals(expected, a.toArray());
	}
	
	@Test
	public void testIndexOfValueThatIsNotInArray() {
		assertEquals(-1, a.indexOf("banana"));
	}
	
	@Test
	public void testIndexOfValueThatIsInArray() {
		assertEquals(0, a.indexOf("Hello"));
	}
	
	@Test
	public void testRemoveFromArrayInvalidIndex() {
		assertThrows(IndexOutOfBoundsException.class, () -> a.remove(100));
	}
	
	@Test
	public void testRemoveFromArrayValidIndex() {
		Object[] expected = new Object[2];
		expected[0] = "Hello";
		expected[1] = 25;
		
		a.remove(2);
		assertArrayEquals(expected, a.toArray());
	}
}
