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

    public static String encryptKey(String text, SecretKeySpec keyspec) throws Exception {
        byte[] encodedBytes = null;
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, keyspec);
            encodedBytes = cipher.doFinal(text.getBytes());
            return toHex(encodedBytes);
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
            decodedBytes = cipher.doFinal(toByte(text));
            return new String(decodedBytes);
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }

        return "fail ded";
    }

//    public static String byte2hex(byte[] b) {
//
//        String hs = "";
//        String stmp = "";
//
//        for (int n = 0; n < b.length; n++) {
//            stmp = Integer.toHexString(b[n] & 0xFF);
//            if (stmp.length() == 1)
//                hs += ("0" + stmp);
//            else
//                hs += stmp;
//        }
//
//        return hs.toUpperCase();
//    }
//
//    public static byte[] hex2byte(byte[] b) {
//        if ((b.length % 2) != 0)
//            throw new IllegalArgumentException("hello");
//
//        byte[] b2 = new byte[b.length / 2];
//
//        for (int n = 0; n < b.length; n += 2) {
//            String item = new String(b, n, 2);
//            b2[n / 2] = (byte) Integer.parseInt(item, 16);
//        }
//
//        return b2;
//    }

    public static String toHex(String txt) {
        return toHex(txt.getBytes());
    }

    public static String fromHex(String hex) {
        return new String(toByte(hex));
    }

    public static byte[] toByte(String hexString) {
        int len = hexString.length() / 2;
        byte[] result = new byte[len];
        for (int i = 0; i < len; i++)
            result[i] = Integer.valueOf(hexString.substring(2 * i, 2 * i + 2),
                    16).byteValue();
        return result;
    }

    public static String toHex(byte[] buf) {
        if (buf == null)
            return "";
        StringBuffer result = new StringBuffer(2 * buf.length);
        for (int i = 0; i < buf.length; i++) {
            appendHex(result, buf[i]);
        }
        return result.toString();
    }

    public SecretKeySpec getSks() {
        return sks;
    }
    private final static String HEX = "0123456789ABCDEF";
    private static void appendHex(StringBuffer sb, byte b) {
        sb.append(HEX.charAt((b >> 4) & 0x0f)).append(HEX.charAt(b & 0x0f));
    }

}
