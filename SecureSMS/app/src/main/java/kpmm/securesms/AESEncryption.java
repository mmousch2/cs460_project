package kpmm.securesms;

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

    public static byte[] encryptKey(String text, SecretKeySpec keyspec) throws Exception {
        byte[] encodedBytes = null;
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, keyspec);
            encodedBytes = cipher.doFinal(text.getBytes());
            return encodedBytes;
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }

        return encodedBytes;
    }

//    public static String encryptKey(String text, SecretKeySpec keyspec) throws Exception {
//        byte[] encodedBytes = null;
//        try {
//            Cipher cipher = Cipher.getInstance("AES");
//            cipher.init(Cipher.ENCRYPT_MODE, keyspec);
//            encodedBytes = cipher.doFinal(text.getBytes());
//            return byte2hex(encodedBytes);
//        } catch (Exception e) {
//            System.out.println(e.getStackTrace());
//        }
//
//        return "fail";
//    }

//    public static String decryptKey(String text, SecretKeySpec keyspec) throws Exception {
//        byte[] decodedBytes = null;
//        try {
//            Cipher cipher = Cipher.getInstance("AES");
//            cipher.init(Cipher.DECRYPT_MODE, keyspec);
//            decodedBytes = cipher.doFinal(hex2byte(text.getBytes()));
//            return new String(decodedBytes);
//        } catch (Exception e) {
//            System.out.println(e.getStackTrace());
//        }
//
//        return "fail";
//    }

    public static String decryptKey(String text, SecretKeySpec keyspec) throws Exception {
        byte[] decodedBytes = null;
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, keyspec);
            decodedBytes = cipher.doFinal(text.getBytes());
            return new String(decodedBytes);
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }

        return "fail";
    }

    public static String byte2hex(byte[] b) {

        String hs = "";
        String stmp = "";

        for (int n = 0; n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0xFF);
            if (stmp.length() == 1)
                hs += ("0" + stmp);
            else
                hs += stmp;
        }

        return hs.toUpperCase();
    }

    public static byte[] hex2byte(byte[] b) {
        if ((b.length % 2) != 0)
            throw new IllegalArgumentException("hello");

        byte[] b2 = new byte[b.length / 2];

        for (int n = 0; n < b.length; n += 2) {
            String item = new String(b, n, 2);
            b2[n / 2] = (byte) Integer.parseInt(item, 16);
        }

        return b2;
    }

    public SecretKeySpec getSks() {
        return sks;
    }
}
