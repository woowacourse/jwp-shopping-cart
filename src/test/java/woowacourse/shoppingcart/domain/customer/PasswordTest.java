package woowacourse.shoppingcart.domain.customer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import woowacourse.shoppingcart.exception.PasswordMisMatchException;

class PasswordTest {

    @DisplayName("password를 생성한다.")
    @ParameterizedTest
    @ValueSource(strings = {"abcd1234", "abcdabcd123412341234"})
    void createPassword(final String value) {
        final Password password = new Password(value);

        assertThat(password.getValue()).isEqualTo(value);
    }

    @DisplayName("길이가 맞지 않는 password를 생성한다.")
    @ParameterizedTest
    @ValueSource(strings = {"abcd123", "abcdefghijklmnop12345"})
    void createInvalidLengthUsername(final String value) {
        assertThatThrownBy(() -> new Password(value))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("패턴이 맞지 않는 password을 생성한다.")
    @ParameterizedTest
    @ValueSource(strings = {"한글비밀번호에요", "@!&@#&!@"})
    void createInvalidPatternUsername(final String value) {
        assertThatThrownBy(() -> new Password(value))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("비밀번호가 일치하면 예외를 반환하지 않아야 한다.")
    @Test
    void matchPassword() {
        assertDoesNotThrow(() -> new Password("12341234").match("12341234"));
    }

    @DisplayName("비밀번호가 일치하지 않으면 예외를 반환해야 한다.")
    @Test
    void matchWrongPassword() {
        assertThatThrownBy(() -> new Password("12341234").match("43214321"))
            .hasMessage("비밀번호가 일치하지 않습니다.")
            .isInstanceOf(PasswordMisMatchException.class);
    }

    @DisplayName("비밀번호를 수정한다.")
    @Test
    void update() {
        final Password password = new Password("password1234");
        final String newPassword = "password1111";
        final Password updatePassword = password.update(newPassword);

        assertThat(updatePassword.getValue()).isEqualTo(newPassword);
    }
}
