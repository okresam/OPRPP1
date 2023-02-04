package hr.fer.oprpp1.hw05.crypto;

/**
 * Util class with byte and hex transformation functions.
 */
public class Util {
	
	/**
	 * Generates byte array from given hex-encoded string input.
	 * 
	 * @param keyText - hex-encoded string to be used
	 * @return byte array containing all bytes of the given hex-encoded string input
	 * @throws IllegalArgumentException when the given hex-encoded string is uneven length
	 * or invalid character is provided in the input string
	 */
	public static byte[] hextobyte(String keyText) {
		if (keyText.length() % 2 != 0) {
			throw new IllegalArgumentException();
		}
		
		byte[] arr = new byte[keyText.length() / 2];
		
		for (int i = 0; i < keyText.length(); i += 2) {
			char currChar1 = Character.toUpperCase(keyText.charAt(i));
			char currChar2 = Character.toUpperCase(keyText.charAt(i+1));
			
			if ((currChar1 < 'A' || currChar1 > 'F') && (currChar1 < '0' || currChar1 > '9')
				&& (currChar2 < 'A' || currChar2 > 'F') && (currChar2 < '0' || currChar2 > '9')) {
				throw new IllegalArgumentException();
			}
			
			arr[i / 2] = (byte) (Character.digit(currChar1, 16) * 16 + Character.digit(currChar2, 16));
		}
		
		return arr;
	}
	
	/**
	 * Generates hex-encoded string for the given byte array.
	 * 
	 * @param bytearray - array of bytes to be used
	 * @return hex-encoded string of the all bytes in the given array
	 */
	public static String bytetohex(byte[] bytearray) {
		String hexStr = "";
		
		for (byte b : bytearray) {
			hexStr += String.format("%02x", b);
		}
		
		return hexStr;
	}
}
