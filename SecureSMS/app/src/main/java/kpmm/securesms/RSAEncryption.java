package kpmm.securesms;

import android.util.Base64;
import android.util.Log;

import java.security.PublicKey;
import java.security.PrivateKey;
import java.security.KeyPair;
import java.security.KeyPairGenerator;

import javax.crypto.Cipher;

/**
 * Created by KP on 1/5/2017.
 */

class RSAEncryption {
    private PublicKey publicKey = null;
    private PrivateKey privateKey = null;

    public RSAEncryption() {
        try {
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
            kpg.initialize(1024);
            KeyPair kp = kpg.generateKeyPair();
            publicKey = kp.getPublic();
            privateKey = kp.getPrivate();
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }
    }

    public static String encryptPublicKey(String text, PublicKey publicRSA) throws Exception {
        byte[] encodedBytes = null;
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicRSA);
            encodedBytes = cipher.doFinal(text.getBytes());
            return byte2hex(encodedBytes);
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }

        return "fail";
    }

    public static String encryptPrivateKey(String text, PrivateKey privateRSA) throws Exception {
        byte[] encodedBytes = null;
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, privateRSA);
            encodedBytes = cipher.doFinal(text.getBytes());
            return byte2hex(encodedBytes);
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }

        return "fail";
    }

    public static String decryptPublicKey(String text, PrivateKey privateRSA) throws Exception {
        byte[] decodedBytes = null;
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateRSA);
            decodedBytes = cipher.doFinal(hex2byte(text.getBytes()));
            return new String(decodedBytes);
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }

        return "fail";
    }

    public static String decryptPrivateKey(String text, PublicKey publicRSA) throws Exception {
        byte[] decodedBytes = null;
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, publicRSA);
            decodedBytes = cipher.doFinal(hex2byte(text.getBytes()));
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

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }
}
