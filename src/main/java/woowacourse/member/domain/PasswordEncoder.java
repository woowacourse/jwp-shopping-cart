package woowacourse.member.domain;

import woowacourse.member.exception.FailedEncryptException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordEncoder {

    private static final String PASSWORD_ENCRYPT_ALGORITHM = "SHA-256";

    public static String encrypt(String text) {
        try {
            MessageDigest md = MessageDigest.getInstance(PASSWORD_ENCRYPT_ALGORITHM);
            md.update(text.getBytes());
            return bytesToHex(md.digest());
        } catch (NoSuchAlgorithmException e) {
            throw new FailedEncryptException();
        }
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }
}
