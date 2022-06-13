package woowacourse.auth.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import woowacourse.shoppingcart.exception.ValidationException;

class UserEmailTest {

    @DisplayName("email은 null이면 예외가 발생한다.")
    @Test
    void notNull() {
        assertThatThrownBy(() -> new UserEmail(null))
                .isInstanceOf(ValidationException.class);
    }

    @DisplayName("email은 빈칸이면 예외가 발생한다.")
    @Test
    void notBlank() {
        assertThatThrownBy(() -> new UserEmail(""))
                .isInstanceOf(ValidationException.class);
    }

    @DisplayName("email은 한글이 포함되면 예외가 발생한다.")
    @Test
    void notKorean() {
        assertThatThrownBy(() -> new UserEmail("안녕@woowa.com"))
                .isInstanceOf(ValidationException.class);
    }

    @DisplayName("email은 email 형식이 아니면 예외가 발생한다.")
    @Test
    void notEmail() {
        assertThatThrownBy(() -> new UserEmail("alien"))
                .isInstanceOf(ValidationException.class);
    }
}
