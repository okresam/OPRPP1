package hr.fer.oprpp1.hw05.crypto;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class UtilTest {

	@Test
	public void testHexToByteUnevenStringGiven() {
		assertThrows(IllegalArgumentException.class, () -> Util.hextobyte("0bc"));
	}
	
	@Test
	public void testHexToByteInvalidCharactersGiven() {
		assertThrows(IllegalArgumentException.class, () -> Util.hextobyte("02GK23"));
	}
	
	@Test
	public void testHexToByteEmptyStringGiven() {
		assertArrayEquals(new byte[0], Util.hextobyte(""));
	}
	
	@Test
	public void testHexToByteValidStringGiven1() {
		assertArrayEquals(new byte[] {1, -82, 34}, Util.hextobyte("01aE22"));
	}
	
	@Test
	public void testHexToByteValidStringGiven2() {
		assertArrayEquals(new byte[] {34, 2, 11}, Util.hextobyte("22020B"));
	}
	
	@Test
	public void testByteToHexEmptyArrayGiven() {
		assertEquals("", Util.bytetohex(new byte[0]));
	}
	
	@Test
	public void testByteToHexValidArrayGiven1() {
		assertEquals("01ae22", Util.bytetohex(new byte[] {1, -82, 34}));
	}
	
	@Test
	public void testByteToHexValidArrayGiven2() {
		assertEquals("22020b", Util.bytetohex(new byte[] {34, 2, 11}));
	}
}
