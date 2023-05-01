package cart.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class PasswordTest {

    @DisplayName("비밀번호가 8글자 미만, 20글자 초과면 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"abcdefg", "abcdefghijklmnopqrxtu"})
    void exceptionWhenWrongPassword(String password) {
        assertThatThrownBy(() -> new Password(password))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("비밀번호는 8글자 ~ 20글자 사이여야 합니다.");
    }

}