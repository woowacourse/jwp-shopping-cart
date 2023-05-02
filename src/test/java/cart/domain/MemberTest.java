package cart.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import cart.exception.ErrorCode;
import cart.exception.GlobalException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class MemberTest {

    @ParameterizedTest(name = "정상적인 사용자 정보가 들어올 경우 예외가 발생하지 않는다.")
    @CsvSource(value = {"a@b.c:12345678:오"}, delimiter = ':')
    void create_success(final String validEmail, final String validNickname) {
        // given
        final Member createdMember = assertDoesNotThrow(() ->
            Member.create(validEmail, "12345678", validNickname, "010-1234-5678",
                MemberRole.USER));

        // when, then
        assertThat(createdMember)
            .extracting("email", "role", "password", "nickname", "telephone")
            .containsExactly(validEmail, MemberRole.USER, "MTIzNDU2Nzg=", validNickname,
                "010-1234-5678");
    }

    @ParameterizedTest(name = "이메일 길이가 5글자 미만, 50글자 초과면 예외가 발생한다.")
    @ValueSource(strings = {"abcd", "abcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdeabcdea"})
    void create_withEmail_fail(final String invalidEmail) {
        assertThatThrownBy(() -> Member.create(invalidEmail, "password", "져니",
            "010-1234-5678", MemberRole.USER))
            .isInstanceOf(GlobalException.class)
            .extracting("errorCode")
            .isEqualTo(ErrorCode.MEMBER_EMAIL_LENGTH);
    }

    @ParameterizedTest(name = "닉네임이 1글자 미만, 30글자 초과면 예외가 발생한다.")
    @ValueSource(strings = {"", "abcdeabcdeabcdeabcdeabcdeabcdea"})
    void create_withNickname_fail(final String invalidNickname) {
        assertThatThrownBy(
            () -> Member.create("journey@gmail.com", "password", invalidNickname,
                "010-1234-5678", MemberRole.USER))
            .isInstanceOf(GlobalException.class)
            .extracting("errorCode")
            .isEqualTo(ErrorCode.MEMBER_NICKNAME_LENGTH);
    }
}
