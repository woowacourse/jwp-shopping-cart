package woowacourse.auth.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import woowacourse.shoppingcart.exception.ValidationException;

class UserPasswordTest {

    @DisplayName("비밀번호가 null이면 예외가 발생한다.")
    @Test
    void nonNull() {
        assertThatThrownBy(() -> new UserPassword(null))
                .isInstanceOf(ValidationException.class);
    }

    @DisplayName("비밀번호가 공백이면 예외가 발생한다.")
    @Test
    void nonBlank() {
        assertThatThrownBy(() -> new UserPassword(""))
                .isInstanceOf(ValidationException.class);
    }

    @DisplayName("비밀번호의 길이가 잘못되면 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(ints = {5, 257})
    void invalidLength(int count) {
        assertThatThrownBy(() -> new UserPassword("a".repeat(count)))
                .isInstanceOf(ValidationException.class);
    }

    @DisplayName("비밀번호에 한글이 포함되면 예외가 발생한다.")
    @Test
    void nonKorean() {
        assertThatThrownBy(() -> new UserPassword("비밀번호입니다비밀번호입니다"))
                .isInstanceOf(ValidationException.class);
    }
}
