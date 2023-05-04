package cart.domain.member;

import cart.exception.InvalidMemberException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class EmailTest {

    @Test
    @DisplayName("email이 빈 칸일 경우 예외가 발생한다.")
    void validateEmailNotBlankTest() {
        // given
        String email = "";

        // then
        assertThatThrownBy(() -> new Email(email))
                .isInstanceOf(InvalidMemberException.class)
                .hasMessage("이메일 주소를 입력하세요.");
    }

    @Test
    @DisplayName("email이 email 형식이 아닐 경우 예외가 발생한다.")
    void validateEmailFormatTest() {
        // given
        String email = "invalidEmail";

        // then
        assertThatThrownBy(() -> new Email(email))
                .isInstanceOf(InvalidMemberException.class)
                .hasMessage("유효하지 않은 email 형식입니다.");
    }

    @Test
    @DisplayName("email이 50자 초과일 경우 예외가 발생한다.")
    void validateEmailLengthTest() {
        // given
        int emailLength = 51;
        String email = "a".repeat(emailLength - 10) + "@gmail.com";

        // then
        assertThatThrownBy(() -> new Email(email))
                .isInstanceOf(InvalidMemberException.class)
                .hasMessage("email은 50자 이하여야 합니다. (현재 " + emailLength + "자)");
    }
}