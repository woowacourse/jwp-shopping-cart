package woowacourse.auth.support;

import org.springframework.stereotype.Component;
import woowacourse.shoppingcart.domain.customer.EncodePassword;
import woowacourse.shoppingcart.domain.customer.PasswordEncoder;
import woowacourse.shoppingcart.domain.customer.RawPassword;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class HashPasswordEncoder implements PasswordEncoder {
    @Override
    public EncodePassword encode(RawPassword rawPassword) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(rawPassword.getPassword().getBytes());
            return new EncodePassword(bytesToHex(messageDigest.digest()));
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("비밀번호 암호화 과정 중 오류가 발생했습니다.");
        }
    }

    private String bytesToHex(byte[] encodedBytes) {
        StringBuilder builder = new StringBuilder();
        for (byte encodedByte : encodedBytes) {
            builder.append(String.format("%02x", encodedByte));
        }
        return builder.toString();
    }
}
