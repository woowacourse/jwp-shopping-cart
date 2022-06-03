package woowacourse.shoppingcart.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import woowacourse.shoppingcart.exception.InvalidPasswordException;

class PasswordTest {

    @Test
    @DisplayName("비밀번호에는 한글을 포함할 수 없다.")
    void validateForm() {
        assertThatThrownBy(() -> new Password("가나다라마바사아자"))
                .isInstanceOf(InvalidPasswordException.class)
                .hasMessage("비밀번호는 한글을 포함할 수 없습니다.");
    }

    @Test
    @DisplayName("비밀번호는 공백을 포함할 수 없다.")
    void validateSpace() {
        assertThatThrownBy(() -> new Password("abcdef  ghi"))
                .isInstanceOf(InvalidPasswordException.class)
                .hasMessage("비밀번호는 공백을 포함할 수 없습니다.");
    }

    @Test
    @DisplayName("비밀번호의 길이는 6자 미만일 수 없다.")
    void validateLength() {
        assertThatThrownBy(() -> new Password("abcde"))
                .isInstanceOf(InvalidPasswordException.class)
                .hasMessage("비밀번호는 6자 이상입니다.");
    }
}
