package com.dikshatech.common.utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.log4j.Logger;

public class DesEncrypterDecrypter {

	private Cipher							ecipher;
	private Cipher							dcipher;
	private static Logger					logger		= LoggerUtil.getLogger(DesEncrypterDecrypter.class);
	private static DesEncrypterDecrypter	instance	= null;

	public DesEncrypterDecrypter(SecretKey key) {
		try{
			ecipher = Cipher.getInstance("DES");
			dcipher = Cipher.getInstance("DES");
			ecipher.init(Cipher.ENCRYPT_MODE, key);
			dcipher.init(Cipher.DECRYPT_MODE, key);
		} catch (javax.crypto.NoSuchPaddingException e){} catch (java.security.NoSuchAlgorithmException e){} catch (java.security.InvalidKeyException e){}
	}

	private DesEncrypterDecrypter() {
		try{
			final SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
			final SecretKey key = skf.generateSecret(new DESKeySpec(new byte[] { 0x001, 0x002, 0X002, 0X002, 0X002, 0X002, 0X002, 0X002, 0X002, 0X002, 0X002, 0X002, 0X002, 0X002, 0X002, 0X002 }));
			ecipher = Cipher.getInstance("DES");
			dcipher = Cipher.getInstance("DES");
			ecipher.init(Cipher.ENCRYPT_MODE, key);
			dcipher.init(Cipher.DECRYPT_MODE, key);
		} catch (Exception e){
			logger.error("unable to create the class instance for DesEncrypterDecrypter", e);
		}
	}

	public static DesEncrypterDecrypter getInstance() {
		if (instance == null) instance = new DesEncrypterDecrypter();
		return instance;
	}

	public String encrypt(String str) {
		if (str == null) return str;
		try{
			// Encode the string into bytes using utf-8
			byte[] utf8 = str.getBytes("UTF8");
			// Encrypt
			byte[] enc = ecipher.doFinal(utf8);
			// Encode bytes to base64 to get a string
			return new sun.misc.BASE64Encoder().encode(enc);
		} catch (Exception e){
			logger.error("Unable to encrypt given value:" + str, e);
		}
		return str;
	}

	public String decrypt(String str) {
		if (str == null) return str;
		try{
			// Decode base64 to get bytes
			byte[] dec = new sun.misc.BASE64Decoder().decodeBuffer(str);
			// Decrypt
			byte[] utf8 = dcipher.doFinal(dec);
			// Decode using utf-8
			return new String(utf8, "UTF8");
		} catch (Exception e){
			logger.error("Unable to decrypt given value:" + str, e);
		}
		return str;
	}

	public static void main(String[] args) {
		//System.out.print(DesEncrypterDecrypter.getInstance().encrypt("5000.00"));
		System.out.print(DesEncrypterDecrypter.getInstance().decrypt("1PRgihZQwwRJ58/geSOz/g=="));
	}
}
