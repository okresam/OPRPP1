package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import hr.fer.oprpp1.custom.collections.SimpleHashtable.TableEntry;

public class SimpleHashtableTest {
	
	SimpleHashtable<String, Integer> sh;
	
	@BeforeEach
	public void beforeeach() {
		sh = new SimpleHashtable<>(2);
	}
	
	@Test
	public void testPutEntryWithNullKey() {
		assertThrows(NullPointerException.class, () -> sh.put(null, 5));
	}
	
	@Test
	public void testInitialCapacityIsInvalid() {
		assertThrows(IllegalArgumentException.class, () -> new SimpleHashtable<String, Integer>(-5));
	}
	
	@Test
	public void testGetWhenEntryDoesNotExist() {
		assertEquals(null, sh.get("nekiKey"));
	}
	
	@Test
	public void testGetWhenEntryDoesNotExistAndGivenKeyIsADifferentType() {
		assertEquals(null, sh.get(83));
	}
	
	@Test
	public void testGetWhenEntryExist() {
		sh.put("key", 43);
		assertEquals(43, sh.get("key"));
	}
	
	@Test
	public void testSizeWhenSimpleHashtableIsEmpty() {
		assertEquals(0, sh.size());
	}
	
	@Test
	public void testSizeWhenSimpleHashtableIsNotEmpty() {
		sh.put("key", 43);
		sh.put("text", 22);
		sh.put("josNekiKey", 324);
		assertEquals(3, sh.size());
	}
	
	@Test
	public void testPutWhenEntryForTheKeyAlreadyExists() {
		sh.put("key", 43);
		sh.put("key", 22);
		assertEquals(22, sh.get("key"));
	}
	
	@Test
	public void testContainsKeyWhenKeyDoesNotExist() {
		assertEquals(false, sh.containsKey("nekiKey"));
	}
	
	@Test
	public void testContainsKeyWhenKeyDoesNotExistAndGivenKeyIsADifferentType() {
		assertEquals(false, sh.containsKey(24));
	}
	
	@Test
	public void testContainsKeyWhenKeyExist() {
		sh.put("nekiKey", 439);
		assertEquals(true, sh.containsKey("nekiKey"));
	}
	
	@Test
	public void testContainsValueWhenValueDoesNotExist() {
		assertEquals(false, sh.containsValue(23));
	}
	
	@Test
	public void testContainsValueWhenValueDoesNotExistAndGivenValueIsADifferentType() {
		assertEquals(false, sh.containsValue("neki string"));
	}
	
	@Test
	public void testContainsValueWhenValueExist() {
		sh.put("nekiKey", 439);
		assertEquals(true, sh.containsValue(439));
	}
	
	@Test
	public void testRemoveWhenEntryDoesNotExist() {
		assertEquals(null, sh.remove(23));
	}
	
	@Test
	public void testRemoveWhenEntryDoesNotExistAndGivenKeyIsADifferentType() {
		assertEquals(null, sh.remove("neki string"));
	}
	
	@Test
	public void testRemoveWhenEntryExist() {
		sh.put("nekiKey", 439);
		assertEquals(439, sh.remove("nekiKey"));
	}
	
	@Test
	public void testIsEmptyWhenSimpleHashTableIsEmpty() {
		assertEquals(true, sh.isEmpty());
	}
	
	@Test
	public void testIsEmptyWhenSimpleHashTableIsNotEmpty() {
		sh.put("key", 3214);
		sh.put("key2", 512);
		sh.put("key3", 67);
		sh.put("key4", 4512);
		assertEquals(false, sh.isEmpty());
	}
	
	@Test
	public void testToStringWhenEmpty() {
		assertEquals("[]", sh.toString());
	}
	
	@Test
	public void testToStringWhenNotEmpty() {
		sh.put("key", 3214);
		sh.put("key2", 512);
		sh.put("key3", 67);
		sh.put("key4", 4512);
		assertEquals("[key2=512, key3=67, key4=4512, key=3214]", sh.toString());
	}
	
	@Test
	public void testClearWhenNotEmpty() {
		sh.put("key", 3214);
		sh.put("key2", 512);
		sh.put("key3", 67);
		sh.put("key4", 4512);
		sh.clear();
		assertEquals(0, sh.size());
	}
	
	@Test
	public void testIteratorHasNextWhenSimpleHashtableIsEmpty() {
		Iterator<TableEntry<String, Integer>> it = sh.iterator();
		assertEquals(false, it.hasNext());
	}
	
	@Test
	public void testIteratorHasNextWhenSimpleHashtableIsNotEmpty() {
		sh.put("key", 612);
		Iterator<TableEntry<String, Integer>> it = sh.iterator();
		assertEquals(true, it.hasNext());
	}
	
	@Test
	public void testIteratorHasNextWhenSimpleHashtableIsNotEmptyButModifiedAfterIteratorWasInitialized() {
		sh.put("key", 612);
		Iterator<TableEntry<String, Integer>> it = sh.iterator();
		sh.put("newKey", 18);
		assertThrows(ConcurrentModificationException.class, () -> it.hasNext());
	}
	
	@Test
	public void testIteratorNextWhenSimpleHashtableIsEmpty() {
		Iterator<TableEntry<String, Integer>> it = sh.iterator();
		assertThrows(NoSuchElementException.class, () -> it.next());
	}
	
	@Test
	public void testIteratorNextWhenSimpleHashtableIsNotEmpty() {
		sh.put("key", 311);
		Iterator<TableEntry<String, Integer>> it = sh.iterator();
		assertEquals(311, it.next().getValue());
	}
	
	@Test
	public void testIteratorNextWhenSimpleHashtableIsNotEmptyButModifiedAfterIteratorWasInitialized() {
		sh.put("key", 311);
		Iterator<TableEntry<String, Integer>> it = sh.iterator();
		sh.put("newKey", 18);
		assertThrows(ConcurrentModificationException.class, () -> it.next());
	}
}
