package woowacourse.shoppingcart.domain.customer;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class PasswordSecureHashEncryptor implements PasswordEncryptor {

    @Override
    public String encode(final String value) {
        return encrypt(value);
    }

    @Override
    public boolean matches(final String plainPassword, final String encryptPassword) {
        return encrypt(plainPassword).equals(encryptPassword);
    }

    private String encrypt(final String value) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(value.getBytes(StandardCharsets.UTF_8));
            return String.format("%064x", new BigInteger(1, messageDigest.digest()));
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("해당 알고리즘을 사용할 수 없습니다.");
        }
    }
}
