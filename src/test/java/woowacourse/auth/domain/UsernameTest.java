package woowacourse.auth.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import woowacourse.shoppingcart.exception.ValidationException;

class UsernameTest {

    @DisplayName("username은 null이면 예외가 발생한다.")
    @Test
    void nonNull() {
        assertThatThrownBy(() -> new Username(null))
                .isInstanceOf(ValidationException.class);
    }

    @DisplayName("username은 공백이면 예외가 발생한다.")
    @Test
    void nonBlank() {
        assertThatThrownBy(() -> new Username(""))
                .isInstanceOf(ValidationException.class);
    }

    @DisplayName("username의 길이가 너무 길면 예외가 발생한다.")
    @Test
    void wrongLength() {
        int maxLength = 33;
        assertThatThrownBy(() -> new Username("a".repeat(maxLength)))
                .isInstanceOf(ValidationException.class);
    }
}
