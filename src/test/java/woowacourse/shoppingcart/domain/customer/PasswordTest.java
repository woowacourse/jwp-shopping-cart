package woowacourse.shoppingcart.domain.customer;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import woowacourse.auth.exception.BadRequestException;

@DisplayName("Password 는")
public class PasswordTest {

    @DisplayName("비밀번호가 입력되었을 때")
    @Nested
    class PasswordValidationTest {

        @DisplayName("올바른 비밀번호가 입력되었으면 비밀번호를 해시화하여 저장한디.")
        @Test
        void validPassword() {
            assertThatNoException().isThrownBy(() -> new Password("password&1"));
        }

        @DisplayName("비밀번호가 올바르지 않으면 에러를 던진다")
        @Test
        void invalidPassword() {
            assertThatThrownBy(() -> new Password("invalid"))
                    .isInstanceOf(BadRequestException.class)
                    .hasMessage(Password.INVALID_PASSWORD_FORMAT);
        }
    }
}
