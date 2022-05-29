package woowacourse.auth.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import woowacourse.auth.exception.InvalidEmailException;

class EmailTest {

    @Test
    @DisplayName("이메일을 생성한다.")
    void createEmail() {
        String value = "testemail@email.com";

        assertThatCode(() -> new Email(value))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("이메일에 @가 없는 경우, 예외를 발생한다.")
    void noAtEmailException() {
        String value = "testemailemail.com";

        assertThatExceptionOfType(InvalidEmailException.class)
                .isThrownBy(() -> new Email(value));
    }

    @Test
    @DisplayName("이메일이 .com 으로 끝나지 않는 경우 예외를 발생한다.")
    void noTrailingWithDotComException() {
        String value = "testemail@email";

        assertThatExceptionOfType(InvalidEmailException.class)
                .isThrownBy(() -> new Email(value));
    }
}
