package kpmm.securesms;

import android.util.Base64;

import java.security.KeyStore;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by KP on 2/5/2017.
 */

public class AESEncryption {
    private SecretKeySpec sks = null;
    private static final byte[] ivBytes = {0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};

    public AESEncryption() {
        try {
            SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
            sr.setSeed("FOR THE LOVE OF SECURITY AND BUZZWORDS LET THIS WORK".getBytes());
            KeyGenerator kg = KeyGenerator.getInstance("AES");
            kg.init(256, sr);
            SecretKey key = kg.generateKey();
            sks = new SecretKeySpec(key.getEncoded(), "AES");

            KeyStore ks = KeyStore.getInstance("AndroidKeyStore");
            boolean success = ks.put("AESKeyCS460", key.getEncoded());
            if (!success) {
                int errorCode = ks.getLastError();
                throw new RuntimeException("Keystore Error: " + errorCode);
            }
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }
    }

    public static String encryptKey(String text, SecretKeySpec keyspec) throws Exception {
        byte[] encodedBytes = null;
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
            cipher.init(Cipher.ENCRYPT_MODE, keyspec);
            encodedBytes = cipher.doFinal(text.getBytes("UTF-8"));
            return Base64.encodeToString(encodedBytes, Base64.NO_WRAP);
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }

        return "fail";
    }

    public static String decryptKey(String text, SecretKeySpec keyspec) throws Exception {
        byte[] decodedBytes = null;
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
            cipher.init(Cipher.DECRYPT_MODE, keyspec);
            decodedBytes = cipher.doFinal(Base64.decode(text, Base64.NO_WRAP));
            return new String(decodedBytes, "UTF-8");
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }

        return "fail ded";
    }

    public SecretKeySpec getSks() {
        return sks;
    }
}
