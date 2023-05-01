package cart.domain.user;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class EmailTest {

    @ParameterizedTest
    @CsvSource({"odo27@naver.com", "odomoon@gmail.com", "nunu@gmail.com"})
    void 유효한_email은_예외를_발생시키지_않는다(final String email) {
        assertThatCode(() -> new Email(email))
                .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @CsvSource({"odo27@", "odomoon", "nunu@gmail"})
    void 유효하지_않은_email은_예외를_발생시킨다(final String email) {
        assertThatThrownBy(() -> new Email(email))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이메일 형식이 올바르지 않습니다.");
    }
}
