package cart.domain;

import cart.exception.InvalidMemberException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertThrows;

class MemberTest {

    @DisplayName("유효하지 않은 email이 들어올 시 InvalidMemberException을 반환한다.")
    @ParameterizedTest
    @ValueSource(strings = {" ", "  ", "notEmailForm"})
    void validateEmail(String email) {
        assertThrows(
                InvalidMemberException.class,
                () -> new Member(1L, email, "password1")
        );
    }

    @DisplayName("유효하지 않은 password가 들어올 시 InvalidMemberException을 반환한다.")
    @ParameterizedTest
    @ValueSource(strings = {" ", "  ", "12345678", "abcdefgh", "a123456"})
    void validatePassword(String password) {
        assertThrows(
                InvalidMemberException.class,
                () -> new Member(1L, "a@a.com", password)
        );
    }
}
