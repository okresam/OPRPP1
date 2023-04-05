package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DictionaryTest {

    Dictionary<String, Integer> d;
	
	@BeforeEach
	public void beforeeach() {
		d = new Dictionary<>();
	}
	
	@Test
	public void testPutPairWithNullKey() {
		assertThrows(NullPointerException.class, () -> d.put(null, 5));
	}
	
	@Test
	public void testGetWhenPairDoesNotExist() {
		assertEquals(null, d.get("key"));
	}
	
	@Test
	public void testGetWhenPairExist() {
		d.put("key", 25);
		assertEquals(25, d.get("key"));
	}
	
	@Test
	public void testRemoveWhenPairDoesNotExist() {
		assertEquals(null, d.remove("key"));
	}
	
	@Test
	public void testRemoveWhenPairExist() {
		d.put("random", 42);
		assertEquals(42, d.remove("random"));
	}
	
	@Test
	public void testIsEmptyWhenDictionaryIsEmpty() {
		assertEquals(true, d.isEmpty());
	}
	
	@Test
	public void testIsEmptyWhenDictionaryIsNotEmpty() {
		d.put("test", 2);
		d.put("nekiKey", 83);
		assertEquals(false, d.isEmpty());
	}
	
	@Test
	public void testSizeWhenDictionaryIsNotEmpty() {
		assertEquals(0, d.size());
	}
	
	@Test
	public void testSizeWhenDictionaryHasThreeElements() {
		d.put("test", 2);
		d.put("nekiKey", 83);
		d.put("noviKey", 29);
		assertEquals(3, d.size());
	}
	
	@Test
	public void testClearWhenDictionaryHasThreeElements() {
		d.put("test", 2);
		d.put("nekiKey", 83);
		d.put("noviKey", 29);
		d.clear();
		assertEquals(0, d.size());
	}
	
	@Test
	public void testPutWhenKeyAlreadyExistsInDictionary() {
		d.put("nekiKey", 2);
		d.put("nekiKey", 99);
		assertEquals(99, d.get("nekiKey"));
	}
	
	@Test
	public void testGetWhenPairExistAndDictionaryHasMultiplePairsStored() {
		d.put("test", 2);
		d.put("nekiKey", 83);
		d.put("noviKey", 29);
		assertEquals(83, d.get("nekiKey"));
	}
	
	@Test
	public void testRemoveWhenPairExistAndDictionaryHasMultiplePairsStored() {
		d.put("key", 25);
		d.put("test", 2);
		d.put("nekiKey", 83);
		d.put("noviKey", 29);
		assertEquals(29, d.remove("noviKey"));
	}
}
