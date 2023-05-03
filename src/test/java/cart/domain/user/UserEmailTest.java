package cart.domain.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class UserEmailTest {

    @Test
    @DisplayName("이메일이 null 일 경우 예외 발생")
    void emailNull() {
        assertThatThrownBy(() -> new UserEmail(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("이메일의 길이가 0일 경우 예외 발생")
    void emailEmpty() {
        assertThatThrownBy(() -> new UserEmail(""))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("이메일의 길이가 32 초과일 경우 예외 발생")
    void emailLengthMoreThanMax() {
        final String email = "a".repeat(24) + "@mail.com";
        assertThatThrownBy(() -> new UserEmail(email))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("이메일의 길이가 정상이지만 형식이 잘못되었을 경우 예외 발생")
    void emailPatternWrong() {
        final String email = "a".repeat(30);
        assertThatThrownBy(() -> new UserEmail(email))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("이메일의 길이와 형식이 정상일 경우")
    void emailSuccess() {
        assertAll(
                () -> assertDoesNotThrow(() -> new UserEmail("e@mail.com")),
                () -> assertDoesNotThrow(() -> new UserEmail("e".repeat(23) + "@mail.com"))
        );
    }
}
