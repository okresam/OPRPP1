package hr.fer.oprpp1.hw05.crypto;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Scanner;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Crypto program for encrypting and decrypting data.
 */
public class Crypto {

	public static void main(String[] args) {
		if (args.length < 2 || args.length > 3) {
			System.out.println("Invalid number of arguments!");
			System.exit(0);
		}
		
		Scanner sc = new Scanner(System.in);
		String expectedSha256Digest = "";
		String keyText = "";
		String ivText = "";
		boolean encrypt = false;
		
		switch(args[0]) {
			case "checksha":
				if (args.length != 2) {
					System.out.println("Invalid number of arguments for given command!");
					System.exit(0);
				}
				
				System.out.printf("Please provide expected sha-256 digest for %s:\n", args[1]);
				System.out.printf("> ");
				expectedSha256Digest = sc.next();
				
				break;
			case "encrypt":
			case "decrypt":
				if (args.length != 3) {
					System.out.println("Invalid number of arguments for given command!");
					System.exit(0);
				}
				
				encrypt = args[0].equals("encrypt") ? true : false;
				
				System.out.println("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):");
				System.out.printf("> ");
				keyText = sc.next();
				
				System.out.println("Please provide initialization vector as hex-encoded text (32 hex-digits):");
				System.out.printf("> ");
				ivText = sc.next();
				
				break;
			default:
				System.out.println("Invalid input!");
				sc.close();
				System.exit(0);
		}
		
		sc.close();
		
		if (!expectedSha256Digest.equals("")) {
			try {
				InputStream input = Files.newInputStream(Paths.get(args[1]));
				MessageDigest sha = MessageDigest.getInstance("SHA-256");
				
				byte[] buffer = new byte[16];
				while (input.read(buffer) != -1)
					sha.update(buffer);
				
				byte[] hash = sha.digest();
				String hexHash = Util.bytetohex(hash);
				
				if (hexHash.equals(expectedSha256Digest)) {
					System.out.printf("Digestion completed. Digest of %s matches expected digest.", args[1]);
				} else {
					System.out.printf("Digestion completed. Digest of %s does not match the expected digest.\nDigest was: %s", args[1], hexHash);
				}
				input.close();
				
			} catch (IOException e) {
				System.out.println("Can't access input file!");
			} catch (NoSuchAlgorithmException e) {
				System.out.println("Error occurred while calculating the digest!");
			}
		} else {
			
			try {
				SecretKeySpec keySpec = new SecretKeySpec(Util.hextobyte(keyText), "AES");
				AlgorithmParameterSpec paramSpec = new IvParameterSpec(Util.hextobyte(ivText));
				Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
				cipher.init(encrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, paramSpec);
				
				InputStream input = Files.newInputStream(Paths.get(args[1]));
				OutputStream output = Files.newOutputStream(Paths.get(args[2]), StandardOpenOption.CREATE_NEW);
				
				byte[] buffer = new byte[16];
				while (input.read(buffer) != -1) {
					output.write(cipher.update(buffer));
				}
				output.write(cipher.doFinal());
				
				if (encrypt) {
					System.out.printf("Encryption completed. ");
				} else {
					System.out.printf("Decryption completed. ");
				}
				
				System.out.printf("Generated file %s based on file %s.", args[2], args[1]);
				
				input.close();
				output.close();
				
			} catch (IOException e) {
				System.out.println("Error while reading a file!");
			} catch (Exception e) {
				System.out.println("Error occurred while encrypting/decrypting!");
			}
			
		}
		
		
	}

}
