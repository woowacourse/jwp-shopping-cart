package woowacourse.common.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryptAlgorithm {

    private static final String ALGORITHM = "SHA-256";
    private static final String FORMAT = "%02x";

    private EncryptAlgorithm() {
    }

    public static String encrypt(String password) {
        try {
            MessageDigest digestAlgorithm = MessageDigest.getInstance(ALGORITHM);
            digestAlgorithm.update(password.getBytes());
            return bytesToHex(digestAlgorithm.digest());
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("적합한 알고리즘을 찾을 수 없습니다.");
        }
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte it : bytes) {
            builder.append(String.format(FORMAT, it));
        }
        return builder.toString();
    }

    public static boolean match(String rawPassword, String encodedPassword) {
        return encrypt(rawPassword).equals(encodedPassword);
    }
}
