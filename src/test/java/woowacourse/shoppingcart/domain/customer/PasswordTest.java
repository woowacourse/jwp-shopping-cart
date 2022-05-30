package woowacourse.shoppingcart.domain.customer;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import woowacourse.auth.exception.InvalidAuthException;
import woowacourse.auth.support.EncryptPasswordEncoder;

public class PasswordTest {

    @ParameterizedTest
    @ValueSource(strings = {"abcdefg", "abcdefghijklmnopqrstu"})
    @DisplayName("비밀번호가 8자 미만 20자 초과 시 예외 발생")
    void invalidPasswordLength_throwException(String password) {
        assertThatThrownBy(() -> Password.purePassword(password))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("비밀번호는 8자 이상 20자 이하입니다.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"abcdefgh", "12345678", "abcdefg123@"})
    @DisplayName("비밀번호 영어, 숫자 미포함 시 예외 발생")
    void invalidPasswordPattern_thrownException(String password) {
        assertThatThrownBy(() -> Password.purePassword(password))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("패스워드는 숫자와 영어를 포함해야합니다.");
    }

    @Test
    @DisplayName("정상적인 패스워드 생성")
    void createPassword() {
        assertDoesNotThrow(() -> Password.purePassword("password123"));
    }

    @Test
    @DisplayName("비밀번호 불일치 시 예외 발생")
    void invalidMatchPassword_throwException() {
        PasswordEncoder passwordEncoder = new EncryptPasswordEncoder();
        Password password = Password.purePassword("password123");

        assertThatThrownBy(() -> password.matchPassword(passwordEncoder, "123password"))
                .isInstanceOf(InvalidAuthException.class)
                .hasMessage("비밀번호가 일치하지 않습니다.");
    }
}
