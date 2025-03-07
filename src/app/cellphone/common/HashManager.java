package app.cellphone.common;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class HashManager {
	public static String getSalt() {
		SecureRandom secureRandom = new SecureRandom();
		
		byte[] salt = new byte[20];
		
		secureRandom.nextBytes(salt);
		
		return Base64.getEncoder().encodeToString(salt);
	}
	
	public static String hashPassword(String password, String salt) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-512");
			
			String saltedPassword = password + salt;
			byte[] hashedBytes = md.digest(saltedPassword.getBytes());
			
			return Base64.getEncoder().encodeToString(hashedBytes);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("패스워드를 해싱하는 데 실패하였습니다.");
		}
	}
}
