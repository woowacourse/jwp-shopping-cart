package woowacourse.auth.support;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.springframework.stereotype.Component;
import woowacourse.shoppingcart.domain.customer.EncryptedPassword;
import woowacourse.shoppingcart.domain.customer.PlainPassword;

@Component
public class Encryption {

    public EncryptedPassword encrypt(PlainPassword password) {
        return new EncryptedPassword(encrypt(password.getPassword()));
    }

    public String encrypt(String text) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(text.getBytes());
            return bytesToHex(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException();
        }
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }

    public boolean isSame(String encodedText, String plainText) {
        return encrypt(plainText).equals(encodedText);
    }
}
