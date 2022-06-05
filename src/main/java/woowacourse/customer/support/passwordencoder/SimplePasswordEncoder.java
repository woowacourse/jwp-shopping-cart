package woowacourse.customer.support.passwordencoder;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Component;

@Component
public class SimplePasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(final String raw) {
        try {
            final MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(raw.getBytes());
            return bytesToHex(md.digest());
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException();
        }
    }

    private String bytesToHex(final byte[] bytes) {
        final StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }

    @Override
    public boolean matches(final String raw, final String encoded) {
        return encoded.equals(encode(raw));
    }
}
