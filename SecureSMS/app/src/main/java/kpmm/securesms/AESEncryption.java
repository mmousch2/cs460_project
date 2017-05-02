package kpmm.securesms;

import android.util.Base64;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by KP on 2/5/2017.
 */

public class AESEncryption {
    private SecretKeySpec sks = null;

    public AESEncryption() {
        try {
            SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
            sr.setSeed("FOR THE LOVE OF SECURITY AND BUZZWORDS LET THIS WORK".getBytes());
            KeyGenerator kg = KeyGenerator.getInstance("AES");
            kg.init(256, sr);
            sks = new SecretKeySpec((kg.generateKey()).getEncoded(), "AES");
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }
    }

    public static String encryptKey(String text, SecretKeySpec keyspec) throws Exception {
        byte[] encodedBytes = null;
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, keyspec);
            encodedBytes = cipher.doFinal(text.getBytes());
            return Base64.encodeToString(encodedBytes, Base64.NO_WRAP);
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }

        return "fail";
    }

    public static String decryptKey(String text, SecretKeySpec keyspec) throws Exception {
        byte[] decodedBytes = null;
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, keyspec);
            decodedBytes = cipher.doFinal(Base64.decode(text, Base64.NO_WRAP));
            return new String(decodedBytes);
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }

        return "fail ded";
    }

    public SecretKeySpec getSks() {
        return sks;
    }
}
