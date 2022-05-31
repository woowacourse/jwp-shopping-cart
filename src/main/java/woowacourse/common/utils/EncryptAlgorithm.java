package woowacourse.common.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryptAlgorithm {

    private EncryptAlgorithm() {
    }

    public static String encrypt(String password) {
        try {
            MessageDigest digestAlgorithm = MessageDigest.getInstance("SHA-256");
            digestAlgorithm.update(password.getBytes());
            return bytesToHex(digestAlgorithm.digest());
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("적합한 알고리즘을 찾을 수 없습니다.");
        }
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte it : bytes) {
            builder.append(String.format("%02x", it));
        }
        return builder.toString();
    }

    public static boolean match(String rawPassword, String encodedPassword) {
        return encrypt(rawPassword).equals(encodedPassword);
    }
}
