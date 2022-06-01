package woowacourse.auth.support;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncoder {

    private static final String BYTE_TO_HEX = "%02x";
    private static final String HASH_ALGORITHM = "SHA-256";

    public String encode(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance(HASH_ALGORITHM); // or MD5, SHA-1
            md.update(password.getBytes());
            return bytesToHex(md.digest());
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException();
        }
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            builder.append(String.format(BYTE_TO_HEX, b));
        }
        return builder.toString();
    }

}
