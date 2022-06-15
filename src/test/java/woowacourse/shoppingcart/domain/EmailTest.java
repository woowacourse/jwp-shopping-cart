package woowacourse.shoppingcart.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import woowacourse.exception.badRequest.EmailFormattingException;

public class EmailTest {

    @DisplayName("이메일 형식 올바르지 않을 경우, 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"1", "2@", "woowahan", "woowahan@woowahan", "woowahan@woowahan."})
    void emailWithException(String email) {
        assertThatThrownBy(() -> new Email(email))
                .isInstanceOf(EmailFormattingException.class);
    }

    @DisplayName("이메일 형식이 올바를 경우, 이메일 객체가 생성된다.")
    @ParameterizedTest
    @ValueSource(strings = {"woo@naver.com", "woo@google.com", "woo@woowahan.com"})
    void email(String email) {
        assertThat(new Email(email).getEmail()).isEqualTo(email);
    }
}
