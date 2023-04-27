package cart.domain.member;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class PasswordTest {

    @Test
    @DisplayName("패스워드를 정상적으로 생성한다.")
    void create_password_success() {
        // given
        String password = "!@a12345";

        // when & then
        assertDoesNotThrow(() -> new Password(password));
    }

    @ParameterizedTest
    @ValueSource(strings = {"!@123456", "abcd1234", "!@abcdef", "!!a1"})
    @DisplayName("패스워드의 조건이 맞지 않아 생성할 수 없다.")
    void throws_exception_when_password_is_wrong(final String givenPassword) {
        // when & then
        assertThatThrownBy(() -> new Password(givenPassword))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
