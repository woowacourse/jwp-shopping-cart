package woowacourse.auth.support;

import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class PasswordEncoder {

    public String encrypt(final String text) {
        try {
            final MessageDigest md = MessageDigest.getInstance("SHA-256"); // or MD5, SHA-1
            md.update(text.getBytes());
            return bytesToHex(md.digest());
        } catch (final NoSuchAlgorithmException e) {
            throw new IllegalStateException();
        }
    }

    // 16진수 형태로 변환
    private String bytesToHex(final byte[] bytes) {
        final StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }

    public boolean matches(final String rawPassword, final String encryptedPassword) {
        return encrypt(rawPassword).equals(encryptedPassword);
    }
}
