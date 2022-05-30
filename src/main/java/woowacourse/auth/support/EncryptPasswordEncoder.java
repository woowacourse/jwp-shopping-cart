package woowacourse.auth.support;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.springframework.stereotype.Component;
import woowacourse.shoppingcart.domain.customer.PasswordEncoder;

@Component
public class EncryptPasswordEncoder implements PasswordEncoder {

    private static final String HASH_ALGORITHM = "SHA-256";
    private static final String BYTE_TO_HEX_FORMAT = "%02x";

    @Override
    public String encode(final String password) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(HASH_ALGORITHM);
            messageDigest.update(password.getBytes());
            return bytesToHex(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("비밀번호를 인코딩할 수 없습니다.");
        }
    }

    private String bytesToHex(byte[] encodedBytes) {
        StringBuilder builder = new StringBuilder();
        for (byte encodedByte : encodedBytes) {
            builder.append(String.format(BYTE_TO_HEX_FORMAT, encodedByte));
        }
        return builder.toString();
    }

    @Override
    public boolean isMatchPassword(final String encodedPassword, final String password) {
        return encodedPassword.equals(encode(password));
    }
}
