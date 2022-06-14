package woowacourse.shoppingcart.customer.domain;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordEncryptUtil {

    public static String encrypt(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(password.getBytes());
            return bytesToHex(md.digest());
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException();
        }
    }

    private static String bytesToHex(byte[] bytes) {
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
