package woowacourse.shoppingcart.domain.customer;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import woowacourse.auth.exception.InvalidAuthException;
import woowacourse.auth.support.EncryptPasswordEncoder;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

public class PasswordTest {

    @Test
    @DisplayName("비밀번호가 64자의 인코딩 길이가 아닌 경우 예외발생")
    void invalidPasswordLength_throwException() {
        assertThatThrownBy(() -> new Password("password123"))
                .isInstanceOf(InvalidCustomerException.class)
                .hasMessage("비밀번호의 길이가 올바르지 않습니다.");
    }

    @Test
    @DisplayName("정상적인 패스워드 생성")
    void createPassword() {
        assertDoesNotThrow(() -> new Password("1234567890123456789012345678901234567890123456789012345678901234"));
    }

    @Test
    @DisplayName("비밀번호 불일치 시 예외 발생")
    void invalidMatchPassword_throwException() {
        PasswordEncoder passwordEncoder = new EncryptPasswordEncoder();
        Password password = passwordEncoder.encode(new PlainPassword("password123"));

        assertThatThrownBy(() -> password.matchPassword(passwordEncoder, new PlainPassword("123password")))
                .isInstanceOf(InvalidAuthException.class)
                .hasMessage("비밀번호가 일치하지 않습니다.");
    }
}
