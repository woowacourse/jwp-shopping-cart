package cart.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import cart.exception.ErrorCode;
import cart.exception.GlobalException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class MemberPasswordTest {

    @ParameterizedTest(name = "정상적인 비밀번호가 들어오면 인코딩된 비밀번호를 반환한다.")
    @CsvSource(value = {"abcdefgh:YWJjZGVmZ2g=",
        "abcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcde:"
            + "YWJjZGVhYmNkZWFiY2RlYWJjZGVhYmNkZWFiY2RlYWJjZGVhYmNkZWFiY2RlYWJjZGU="}, delimiter = ':')
    void create(final String validPassword, final String encodedPassword) {
        // given
        final MemberPassword createdPassword = assertDoesNotThrow(() ->
            MemberPassword.create(validPassword));

        // when, then
        assertThat(createdPassword)
            .extracting("password")
            .isEqualTo(encodedPassword);
    }

    @ParameterizedTest(name = "비밀번호가 8글자 미만, 50글자 초과면 예외가 발생한다.")
    @ValueSource(strings = {"abcdefg", "abcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdea"})
    void create_withPassword_fail(final String invalidPassword) {
        assertThatThrownBy(() -> MemberPassword.create(invalidPassword))
            .isInstanceOf(GlobalException.class)
            .extracting("errorCode")
            .isEqualTo(ErrorCode.MEMBER_PASSWORD_LENGTH);
    }

    @Test
    @DisplayName("디코딩된 비밀번호 값을 반환한다.")
    void decodePassword() {
        // given
        final MemberPassword memberPassword = MemberPassword.create("abcdefgh");

        // when
        final String decodePassword = memberPassword.decodePassword();

        // then
        assertThat(decodePassword)
            .isEqualTo("abcdefgh");
    }
}
