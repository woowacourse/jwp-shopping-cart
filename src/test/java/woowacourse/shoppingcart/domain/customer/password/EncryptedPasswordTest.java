package woowacourse.shoppingcart.domain.customer.password;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import woowacourse.shoppingcart.exception.datanotmatch.CustomerDataNotMatchException;
import woowacourse.shoppingcart.exception.datanotmatch.LoginDataNotMatchException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("EncryptedPassword 도메인 테스트")
class EncryptedPasswordTest {

    @DisplayName("비밀번호와 일치하지 않을 경우 예외가 발생한다.")
    @Test
    void validateMatchingLoginPassword() {
        // given
        Password password = new EncryptedPassword(encrypt("1234asdf!"));

        // when & then
        assertThatThrownBy(() -> password.validateMatchingLoginPassword("invalidPassword"))
                .isInstanceOf(LoginDataNotMatchException.class)
                .hasMessage("비밀번호가 일치하지 않습니다.");
    }

    @DisplayName("비밀번호와 일치하지 않을 경우 예외가 발생한다.")
    @Test
    void validateMatchingOriginalPassword() {
        // given
        Password password = new EncryptedPassword(encrypt("1234asdf!"));

        // when & then
        assertThatThrownBy(() -> password.validateMatchingOriginalPassword("invalidPassword"))
                .isInstanceOf(CustomerDataNotMatchException.class)
                .hasMessage("기존 비밀번호와 입력한 비밀번호가 일치하지 않습니다.");
    }

    private String encrypt(final String text) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(text.getBytes());
            return bytesToHex(md.digest());
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException();
        }
    }

    private String bytesToHex(final byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }
}
