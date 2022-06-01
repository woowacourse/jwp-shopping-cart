package woowacourse.shoppingcart.support;

import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class SHA256Encryptor implements Encryptor {

    private static final String ALGORITHM = "SHA-256";

    @Override
    public String encrypt(final String plainText) {
        try {
            final MessageDigest messageDigest = MessageDigest.getInstance(ALGORITHM);
            messageDigest.update(plainText.getBytes());
            return bytesToHex(messageDigest.digest());
        } catch (final NoSuchAlgorithmException e) {
            throw new RuntimeException();
        }
    }

    private String bytesToHex(final byte[] bytes) {
        final StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }
}
