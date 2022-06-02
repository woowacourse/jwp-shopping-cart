package woowacourse.shoppingcart.domain.vo;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import woowacourse.shoppingcart.exception.EmailValidationException;

class EmailTest {

    @Test
    @DisplayName("이메일 형식의 값 객체를 생성한다.")
    void create() {
        final String rawEmail = "younghwna960@gmail.com";
        final Email email = new Email(rawEmail);

        assertThat(email.getValue()).isEqualTo(rawEmail);
    }

    @Test
    @DisplayName("값이 비어있는 객체를 생성한다.")
    void empty() {
        final Email empty = Email.empty();

        assertThat(empty.getValue()).isNull();
    }

    @Test
    @DisplayName("이메일 형식이 아닌 형태로 객체를 생성하려 하면 예외가 발생한다.")
    void createWithNoEmailForm() {
        assertThatThrownBy(() -> new Email("askjfhabsfafbnsalfbnf"))
                .isInstanceOf(EmailValidationException.class)
                .hasMessageContaining("이메일 형식을 지켜야합니다.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"a@a.com", "abcdeabcdeabcdeabcde@abcdeabcdeabcdeabcdeabcdea.com"})
    @DisplayName("이메일의 길이가 8미만 50초과일 경우 예외가 발생한다.")
    void createWithInvalidLength(final String rawEmail) {
        assertThatThrownBy(() -> new Email(rawEmail))
                .isInstanceOf(EmailValidationException.class)
                .hasMessageContaining("이메일은 8자 이상 50자 이하여야합니다.");
    }
}