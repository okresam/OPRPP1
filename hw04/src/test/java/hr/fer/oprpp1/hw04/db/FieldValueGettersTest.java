package hr.fer.oprpp1.hw04.db;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FieldValueGettersTest {
	
	StudentRecord sr;
	
	@BeforeEach
	public void beforeeach() {
		sr = new StudentRecord("0000000043", "Perica", "Krešimir", "4");
	}
	
	@Test
	public void testGetFirstName() {
		assertEquals("Krešimir", FieldValueGetters.FIRST_NAME.get(sr));
	}
	
	@Test
	public void testGetLastName() {
		assertEquals("Perica", FieldValueGetters.LAST_NAME.get(sr));
	}
	
	@Test
	public void testGetJmbag() {
		assertEquals("0000000043", FieldValueGetters.JMBAG.get(sr));
	}
}
