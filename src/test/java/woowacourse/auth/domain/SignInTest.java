package woowacourse.auth.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import woowacourse.shoppingcart.exception.ValidationException;

class SignInTest {

    @Test
    @DisplayName("잘못된 형식의 이메일을 입력하면 에러가 발생한다.")
    void InvalidEmail() {
        String email = "alien";
        String password = "12345678";

        assertThatThrownBy(() -> new SignIn(email, password))
                .isInstanceOf(ValidationException.class);
    }

    @Test
    @DisplayName("잘못된 형식의 비밀번호을 입력하면 에러가 발생한다.")
    void InvalidPassword() {
        String email = "alien@woowa.com";
        String password = "1234";

        assertThatThrownBy(() -> new SignIn(email, password))
                .isInstanceOf(ValidationException.class);
    }
}
