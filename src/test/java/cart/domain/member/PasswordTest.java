package cart.domain.member;

import cart.exception.InvalidMemberException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PasswordTest {

    @Test
    @DisplayName("비밀번호가 빈 칸일 경우 예외가 발생한다.")
    void validatePasswordNotBlankTest() {
        // given
        String password = "";

        // then
        assertThatThrownBy(() -> new Password(password))
                .isInstanceOf(InvalidMemberException.class)
                .hasMessage("비밀번호를 입력하세요.");
    }

    @Test
    @DisplayName("비밀번호가 50자 초과일 경우 예외가 발생한다.")
    void validateNameLengthTest() {
        // given
        int passwordLength = 51;
        String password = "a".repeat(passwordLength);

        // then
        assertThatThrownBy(() -> new Password(password))
                .isInstanceOf(InvalidMemberException.class)
                .hasMessage("비밀번호는 50자 이하여야 합니다. (현재 " + passwordLength + "자)");
    }
}