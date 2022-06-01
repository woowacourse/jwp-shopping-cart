package woowacourse.shoppingcart.domain.customer;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncoder {

    private static final String ALGORITHM = "SHA-256";
    private static final String FORMAT = "%02x";

    public String encrypt(String password) {
        try {
            MessageDigest digestAlgorithm = MessageDigest.getInstance(ALGORITHM);
            digestAlgorithm.update(password.getBytes());
            return bytesToHex(digestAlgorithm.digest());
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("적합한 알고리즘을 찾을 수 없습니다.");
        }
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte it : bytes) {
            builder.append(String.format(FORMAT, it));
        }
        return builder.toString();
    }

    public boolean match(String rawPassword, String encodedPassword) {
        return encrypt(rawPassword).equals(encodedPassword);
    }
}
