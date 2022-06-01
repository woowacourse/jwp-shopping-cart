package woowacourse.shoppingcart.domain.customer;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class PlainPasswordTest {

    @ParameterizedTest
    @ValueSource(strings = {"abcdefg", "abcdefghijklmnopqrstu"})
    @DisplayName("비밀번호가 8자 미만 20자 초과 시 예외 발생")
    void invalidPasswordLength_throwException(String password) {
        assertThatThrownBy(() -> new PlainPassword(password))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("비밀번호는 8자 이상 20자 이하입니다.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"abcdefgh", "12345678", "abcdefg123@"})
    @DisplayName("비밀번호 영어, 숫자 미포함 시 예외 발생")
    void invalidPasswordPattern_thrownException(String password) {
        assertThatThrownBy(() -> new PlainPassword(password))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("패스워드는 숫자와 영어를 포함해야합니다.");
    }

    @Test
    @DisplayName("정상적인 패스워드 생성")
    void createPassword() {
        assertDoesNotThrow(() -> new PlainPassword("password123"));
    }
}
