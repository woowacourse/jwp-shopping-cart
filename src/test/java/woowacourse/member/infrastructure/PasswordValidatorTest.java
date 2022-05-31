package woowacourse.member.infrastructure;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import woowacourse.member.exception.PasswordNotValidException;

public class PasswordValidatorTest {

    @DisplayName("올바른 형식이 아니면 에러를 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"1!", "maru1234!", "Maru!", "Maru1234"})
    void validate(String value) {
        assertThatThrownBy(() -> new PasswordValidator().validate(value))
                .isInstanceOf(PasswordNotValidException.class);
    }
}
