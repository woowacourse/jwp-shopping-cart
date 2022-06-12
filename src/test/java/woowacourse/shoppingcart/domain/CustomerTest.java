package woowacourse.shoppingcart.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import woowacourse.shoppingcart.exception.InvalidPasswordException;
import woowacourse.shoppingcart.exception.ValidationException;

class CustomerTest {
    @Test
    @DisplayName("잘못된 형식의 유저네임을 입력하면 에러가 발생한다.")
    void InvalidUsername() {
        String username = "a".repeat(33);
        String email = "alien@woowa.com";
        String password = "12345678";

        assertThatThrownBy(() -> new Customer(username, email, password))
                .isInstanceOf(ValidationException.class);
    }

    @Test
    @DisplayName("잘못된 형식의 이메일을 입력하면 에러가 발생한다.")
    void InvalidEmail() {
        String username = "alien";
        String email = "alien";
        String password = "12345678";

        assertThatThrownBy(() -> new Customer(username, email, password))
                .isInstanceOf(ValidationException.class);
    }

    @Test
    @DisplayName("잘못된 형식의 비밀번호을 입력하면 에러가 발생한다.")
    void InvalidPassword() {
        String username = "alien";
        String email = "alien@woowa.com";
        String password = "1234";

        assertThatThrownBy(() -> new Customer(username, email, password))
                .isInstanceOf(ValidationException.class);
    }
}
