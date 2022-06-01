package woowacourse.shoppingcart.domain.customer.password;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class SHA256Encoder implements PasswordEncoder {

    @Override
    public Password encode(Password rawPassword) {
        MessageDigest messageDigest = getMessageDigest();
        String rawValue = rawPassword.getValue();
        messageDigest.update(rawValue.getBytes(StandardCharsets.UTF_8));

        String encodedValue = Arrays.toString(messageDigest.digest());
        return Password.createEncoded(encodedValue);
    }

    private MessageDigest getMessageDigest() {
        try {
            return MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException("유효하지 않은 인코딩 알고리즘입니다.");
        }
    }

    @Override
    public boolean matches(Password rawPassword, Password encodedPassword) {
        return encode(rawPassword).equals(encodedPassword);
    }
}
