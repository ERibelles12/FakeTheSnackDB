package de.pecus.api.encryption.util;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class EncriptionRSABAZUtil {

    public static String getPrivateKeyString(PrivateKey privateKey) {
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(privateKey.getEncoded());
        return bytesToString(pkcs8EncodedKeySpec.getEncoded());
    }

    public static String getPublicKeyString(PublicKey publicKey) {
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(publicKey.getEncoded());
        return bytesToString(x509EncodedKeySpec.getEncoded());
    }

    public static Map<String, Object> genKeyPair(int size) throws NoSuchAlgorithmException {
        Map<String, Object> mapa = new HashMap();
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(size);
        KeyPair kp = kpg.genKeyPair();
        String publicKey = Base64.getMimeEncoder().encodeToString(kp.getPublic().getEncoded());
        String privateKey = Base64.getMimeEncoder().encodeToString(kp.getPrivate().getEncoded());
        mapa.put("public_key", publicKey);
        mapa.put("private_key", privateKey);
        return mapa;
    }

    public static String encryptRSAToBAZ(String plain, String publicKey) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, IOException, InvalidKeySpecException {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(1, stringToPublicKey(bytesToString(Base64.getMimeDecoder().decode(publicKey))));
        byte[] encryptedBytes = cipher.doFinal(plain.getBytes(Charset.forName("UTF-8")));
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public static String decryptRSAToBAZ(String result, String privateKey) {
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(2, stringToPrivateKey(bytesToString(Base64.getMimeDecoder().decode(privateKey))));
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(new String(result.getBytes(), Charset.forName("UTF-8"))));
            return new String(decryptedBytes);
        } catch (Exception e) {
           return e.getMessage();
        }
    }

    public static String bytesToString(byte[] b) {
        byte[] b2 = new byte[b.length + 1];
        b2[0] = 1;
        System.arraycopy(b, 0, b2, 1, b.length);
        return (new BigInteger(b2)).toString(36);
    }

    public static byte[] stringToBytes(String s) {
        byte[] b2 = (new BigInteger(s, 36)).toByteArray();
        return Arrays.copyOfRange(b2, 1, b2.length);
    }

    private static PublicKey stringToPublicKey(String publickey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePublic(new X509EncodedKeySpec(stringToBytes(publickey)));
    }

    private static PrivateKey stringToPrivateKey(String privateKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(new PKCS8EncodedKeySpec(stringToBytes(privateKey)));
    }

    public static PublicKey getPublicKey(String llavePublica) {
        byte[] decode = Base64.getDecoder().decode(llavePublica);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(decode);
        PublicKey pk = null;

        try {
            KeyFactory factory = KeyFactory.getInstance("RSA");
            pk = factory.generatePublic(spec);
            return pk;
        } catch (Exception e) {
           e.printStackTrace();
           return pk;
        }
    }

    public static PrivateKey getPrivateKey(String llavePrivada){
        byte[] decode = Base64.getDecoder().decode(llavePrivada);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(decode);
        PrivateKey pk = null;

        try {
            KeyFactory factory = KeyFactory.getInstance("RSA");
            pk = factory.generatePrivate(spec);
            return pk;
        } catch (Exception e) {
            e.printStackTrace();
            return pk;
        }
    }
}
