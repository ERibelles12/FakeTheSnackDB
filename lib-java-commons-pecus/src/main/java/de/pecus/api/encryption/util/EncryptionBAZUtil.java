package de.pecus.api.encryption.util;

import java.nio.charset.StandardCharsets;
import java.security.spec.KeySpec;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;


public class EncryptionBAZUtil {
	
	public static String BAZ_ENCRYPTION_KEY = "PBKDF2WithHmacSHA256";
	public static String STANDARD_TRANSFORMATION_NAMES= "AES/CBC/PKCS5Padding";

    public static String encryptAESLlavesToBAZ(String llave, String vector) {
        String nuevaLlave = vector;
        try {
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(BAZ_ENCRYPTION_KEY);
            KeySpec keySpec = new PBEKeySpec(nuevaLlave.toCharArray(), nuevaLlave.getBytes(), 65536, 256);
            SecretKey secretKey = secretKeyFactory.generateSecret(keySpec);
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getEncoded(), "AES");
            Cipher cipher = Cipher.getInstance(STANDARD_TRANSFORMATION_NAMES);
            cipher.init(1, secretKeySpec, new IvParameterSpec(nuevaLlave.getBytes()));
            return Base64.getEncoder().encodeToString(cipher.doFinal(llave.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e ) {
            return e.getMessage();
        }
    }

    public static String decryptAESLlavesToBAZ(String textoCifrado, String vector) {
        String nuevaLlave = vector;

        try {
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(BAZ_ENCRYPTION_KEY);
            KeySpec keySpen = new PBEKeySpec(nuevaLlave.toCharArray(), nuevaLlave.getBytes(), 65536, 256);
            SecretKey secretKey = secretKeyFactory.generateSecret(keySpen);
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getEncoded(), "AES");
            Cipher cipher = Cipher.getInstance(STANDARD_TRANSFORMATION_NAMES);
            cipher.init(2, secretKeySpec, new IvParameterSpec(nuevaLlave.getBytes()));
            return new String(cipher.doFinal(Base64.getDecoder().decode(textoCifrado)), StandardCharsets.UTF_8);
        } catch (Exception e ) {
            return e.getMessage();
        }
    }
}

