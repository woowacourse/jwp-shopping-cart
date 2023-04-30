package cart.domain;

import cart.exception.ErrorCode;
import cart.exception.GlobalException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class MemberTest {

    @ParameterizedTest(name = "정상적인 사용자 정보가 들어올 경우 예외가 발생하지 않는다.")
    @CsvSource(value = {"a@b.c:12345678:오",
            "abcdeabcdeabcdeabcdeabcdeabcdeabcdeabcde@fghj.com:" +
                    "abcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcde:" +
                    "abcdeabcdeabcdeabcdeabcdeabcde"}, delimiter = ':')
    void create_success(final String validEmail, final String validPassword, final String validNickname) {
        final Member createdMember = assertDoesNotThrow(() ->
                Member.create(validEmail, validPassword, validNickname, "010-1234-5678"));

        assertThat(createdMember)
                .extracting("email", "password", "nickname", "telephone")
                .containsExactly(validEmail, validPassword, validNickname, "010-1234-5678");
    }

    @ParameterizedTest(name = "이메일 길이가 5글자 미만, 50글자 초과면 예외가 발생한다.")
    @ValueSource(strings = {"abcd", "abcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdea"})
    void create_withEmail_fail(final String invalidEmail) {
        assertThatThrownBy(() -> Member.create(invalidEmail, "password", "져니", "010-1234-5678"))
                .isInstanceOf(GlobalException.class)
                .extracting("errorCode")
                .isEqualTo(ErrorCode.MEMBER_EMAIL_LENGTH);
    }

    @ParameterizedTest(name = "비밀번호가 8글자 미만, 50글자 초과면 예외가 발생한다.")
    @ValueSource(strings = {"abcdefg", "abcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdea"})
    void create_withPassword_fail(final String invalidPassword) {
        assertThatThrownBy(() -> Member.create("journey@gmail.com", invalidPassword, "져니", "010-1234-5678"))
                .isInstanceOf(GlobalException.class)
                .extracting("errorCode")
                .isEqualTo(ErrorCode.MEMBER_PASSWORD_LENGTH);
    }

    @ParameterizedTest(name = "닉네임이 1글자 미만, 30글자 초과면 예외가 발생한다.")
    @ValueSource(strings = {"", "abcdeabcdeabcdeabcdeabcdeabcdea"})
    void create_withNickname_fail(final String invalidNickname) {
        assertThatThrownBy(() -> Member.create("journey@gmail.com", "password", invalidNickname, "010-1234-5678"))
                .isInstanceOf(GlobalException.class)
                .extracting("errorCode")
                .isEqualTo(ErrorCode.MEMBER_NICKNAME_LENGTH);
    }
}
