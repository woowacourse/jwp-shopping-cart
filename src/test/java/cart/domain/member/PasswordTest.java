package cart.domain.member;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class PasswordTest {

    @Test
    @DisplayName("비밀번호를 정상적으로 등록할 수 있다")
    void passwordTest() {
        final String input = "test";

        assertDoesNotThrow(() -> new Password(input));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("비밀번호가 존재하지 않으면 예외가 발생한다")
    void throwExceptionWhenPasswordNotExist(final String input) {
        assertThatThrownBy(() -> new Password(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("비밀번호가 존재하지 않습니다");
    }
}
