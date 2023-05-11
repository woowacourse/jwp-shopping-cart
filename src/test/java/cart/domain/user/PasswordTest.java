package cart.domain.user;

import cart.exception.GlobalException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class PasswordTest {
    @ParameterizedTest(name = "패스워드 생성 테스트")
    @ValueSource(strings = {"p", "password", "01234567890123456789"})
    void createEmail(String input) {
        assertDoesNotThrow(() -> Password.from(input));
    }

    @ParameterizedTest(name = "패스워드의 길이는 1글자 미만, 20글자를 초과할 수 없다.")
    @ValueSource(strings = {"", "012345678901234567890"})
    void createProductNameFailureBlank(String input) {
        assertThatThrownBy(() -> Password.from(input))
                .isInstanceOf(GlobalException.class);
    }
}
