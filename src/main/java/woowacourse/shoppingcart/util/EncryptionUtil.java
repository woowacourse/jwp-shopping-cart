package woowacourse.shoppingcart.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryptionUtil {
    public static String encrypt(String input) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(input.getBytes());
            return bytesToHex(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException();
        }
    }

    public static String bytesToHex(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }

    public static boolean isSameEncryptedPassword(String encryptedPassword, String password) {
        return encryptedPassword.equals(encrypt(password));
    }
}
