package woowacourse.shoppingcart.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashTool {

    private static final String SHA256 = "SHA-256";
    private static final String SALT = "EN_SIX_anPang";
    private static final int KEY_STRETCHING = 6;

    public static String hashing(String password) {
        MessageDigest digest = getInstance(SHA256);

        for (int i = 0; i < KEY_STRETCHING; i++) {
            String temp = password + SALT;
            digest.update(temp.getBytes());
        }
        return byteToString(digest.digest());
    }

    private static MessageDigest getInstance(String algorithm) {
        try {
            return MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException("사용할 수 없는 알고리즘 입니다.");
        }
    }

    private static String byteToString(byte[] digest) {
        StringBuilder sb = new StringBuilder();
        for (byte aByte : digest) {
            sb.append(String.format("%02x", aByte));
        }
        return sb.toString();
    }
}
