package cart.domain.user;

import static java.lang.System.lineSeparator;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class EmailValidatorTest {

    @DisplayName("이메일 형식에 맞는 문자열을 입력 받으면 생성에 성공한다.")
    @Test
    void shouldPassWhenInputAppropriateEmailText() {
        assertDoesNotThrow(() -> EmailValidator.validate("test@test.test"));
    }

    @DisplayName("@가 포함되지 않으면 예외가 발생한다.")
    @Test
    void shouldThrowIllegalArgumentExceptionWhenEmailTextDoesNotHaveAtSign() {
        String wrongEmail = "test.test";
        assertThatThrownBy(() -> EmailValidator.validate(wrongEmail))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이메일 형식이 잘못되었습니다." + lineSeparator()
                        + "입력된 이메일: " + wrongEmail);
    }

    @DisplayName(".이 포함되지 않으면 예외가 발생한다.")
    @Test
    void shouldThrowIllegalArgumentExceptionWhenEmailTextDoesNotHavePoint() {
        String wrongEmail = "test@test";
        assertThatThrownBy(() -> EmailValidator.validate(wrongEmail))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이메일 형식이 잘못되었습니다." + lineSeparator()
                        + "입력된 이메일: " + wrongEmail);
    }
}
