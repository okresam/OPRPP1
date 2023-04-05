package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LinkedListIndexedCollectionTest {

	LinkedListIndexedCollection l;
	
	@BeforeEach
	public void beforeeach() {
		l = new LinkedListIndexedCollection();
		l.add("Apple");
		l.add(53);
		l.add("Book");
	}
	
	@Test
	public void testConstructorCopiesOtherCollectionToThisNewList() {
		LinkedListIndexedCollection newList = new LinkedListIndexedCollection(l);
		assertArrayEquals(l.toArray(), newList.toArray());
	}
	
	@Test
	public void testAddNullToList() {
		assertThrows(NullPointerException.class, () -> l.add(null));
	}
	
	@Test
	public void testAddValidValueToList() {
		Object[] expected = new Object[4];
		expected[0] = "Apple";
		expected[1] = 53;
		expected[2] = "Book";
		expected[3] = 27;
		
		l.add(27);
		assertArrayEquals(expected, l.toArray());
	}
	
	@Test
	public void testGetFromListInvalidIndex() {
		assertThrows(IndexOutOfBoundsException.class, () -> l.get(100));
	}
	
	@Test
	public void testGetFromListValidIndex() {
		assertEquals("Apple", l.get(0));
	}
	
	@Test
	public void testClearList() {
		l.clear();
		assertEquals(0, l.size());
	}
	
	@Test
	public void testInsertIntoListOnInvalidPosition() {
		assertThrows(IndexOutOfBoundsException.class, () -> l.insert("Monkey", 200));
	}
	
	@Test
	public void testInsertNullIntoList() {
		assertThrows(NullPointerException.class, () -> l.insert(null, 0));
	}
	
	@Test
	public void testInsertIntoListOnValidPosition() {
		Object[] expected = new Object[4];
		expected[0] = "Apple";
		expected[1] = 53;
		expected[2] = "Book";
		expected[3] = "Lion";
		
		String s = "Lion";
		l.insert(s, 3);
		
		assertArrayEquals(expected, l.toArray());
	}
	
	@Test
	public void testIndexOfValueThatIsNotInList() {
		assertEquals(-1, l.indexOf("Kiwi"));
	}
	
	@Test
	public void testIndexOfValueThatIsInList() {
		assertEquals(2, l.indexOf("Book"));
	}
	
	@Test
	public void testRemoveFromListInvalidIndex() {
		assertThrows(IndexOutOfBoundsException.class, () -> l.remove(-7));
	}
	
	@Test
	public void testRemoveFromListValidIndex() {
		Object[] expected = new Object[2];
		expected[0] = "Apple";
		expected[1] = "Book";
		
		l.remove(1);
		assertArrayEquals(expected, l.toArray());
	}
	
}
