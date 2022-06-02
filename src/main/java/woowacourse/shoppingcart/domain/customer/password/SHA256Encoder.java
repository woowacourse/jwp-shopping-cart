package woowacourse.shoppingcart.domain.customer.password;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import org.springframework.stereotype.Component;

@Component
public class SHA256Encoder implements PasswordEncoder {

    @Override
    public String encode(String rawPassword) {
        MessageDigest messageDigest = getMessageDigest();
        messageDigest.update(rawPassword.getBytes(StandardCharsets.UTF_8));

        return Arrays.toString(messageDigest.digest());
    }

    private MessageDigest getMessageDigest() {
        try {
            return MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException("유효하지 않은 인코딩 알고리즘입니다.");
        }
    }

    @Override
    public boolean matches(String rawPassword, String encodedPassword) {
        return encode(rawPassword).equals(encodedPassword);
    }
}
