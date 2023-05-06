package cart.domain.user.validator;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class PasswordValidatorTest {

    @DisplayName("값이 비어있으면 예외가 발생한다.")
    @ParameterizedTest(name = "비어있는 값 (\"{0}\")")
    @ValueSource(strings = {" ", "  "})
    @NullAndEmptySource
    void shouldThrowIllegalArgumentExceptionWhenInputNullOrBlankValue(String inputPassword) {
        assertThatThrownBy(() -> PasswordValidator.validate(inputPassword))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("비밀번호 입력이 비어있습니다.");
    }
}
