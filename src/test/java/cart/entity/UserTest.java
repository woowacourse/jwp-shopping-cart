package cart.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UserTest {

    @Test
    @DisplayName("사용자 정상 생성 테스트")
    void user_test() {
        final String email = "test@email.com";
        final String password = "12345";

        assertThatNoException().isThrownBy(() -> new User(email, password));
    }

    @Test
    @DisplayName("비밀번호가 5자 미만이면 예외 발생")
    void password_length_validation_test() {
        final String email = "test@email.com";
        final String password = "1234";

        assertThatThrownBy(() -> new User(email, password))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("password가 5글자 미만입니다.");

    }

}
