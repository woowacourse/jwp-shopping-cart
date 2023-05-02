package cart.domain.member;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class EmailTest {

    @Test
    @DisplayName("이메일을 정상적으로 등록할 수 있다")
    void emailTest() {
        final String input = "test123@test.com";

        assertDoesNotThrow(() -> new Email(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"@test.com", "test@test", "test.com"})
    @DisplayName("유효하지 않은 이메일 형식일 경우 예외가 발생한다")
    void throwExceptionWhenInvalidEmail(final String input) {
        assertThatThrownBy(() -> new Email(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("유효하지 않은 이메일 형식입니다");
    }
}
