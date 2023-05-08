package cart.domain.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class UserPasswordTest {

    @Test
    @DisplayName("비밀번호가 null 일 경우 예외 발생")
    void passwordNull() {
        assertThatThrownBy(() -> new UserPassword(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("비밀번호의 길이가 0일 경우 예외 발생")
    void passwordEmpty() {
        assertThatThrownBy(() -> new UserPassword(""))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("비밀번호의 길이가 32 초과일 경우 예외 발생")
    void passwordLengthMoreThanMax() {
        final String password = "a".repeat(33);
        assertThatThrownBy(() -> new UserPassword(password))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("비밀번호의 길이가 정상일 경우")
    void passwordSuccess() {
        assertAll(
                () -> assertDoesNotThrow(() -> new UserPassword("a")),
                () -> assertDoesNotThrow(() -> new UserPassword("a".repeat(32)))
        );
    }
}
