package woowacourse.shoppingcart.domain.customer;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import woowacourse.shoppingcart.exception.PasswordMisMatchException;

class PasswordTest {

    @DisplayName("password를 생성한다.")
    @ParameterizedTest
    @ValueSource(strings = {"abcd1234", "abcdabcd123412341234"})
    void createPassword(String value) {
        Password password = Password.from(value);
        assertThat(password).isEqualTo(Password.from(value));
    }

    @DisplayName("비밀번호가 일치하면 예외를 반환하지 않아야 한다.")
    @Test
    void matchPassword() {
        assertDoesNotThrow(() -> Password.from("password1234").match("password1234"));
    }

    @DisplayName("비밀번호가 일치하지 않으면 예외를 반환해야 한다.")
    @Test
    void matchWrongPassword() {
        assertThatThrownBy(() -> Password.from("password1234").match("password4321"))
            .hasMessage("비밀번호가 일치하지 않습니다.")
            .isInstanceOf(PasswordMisMatchException.class);
    }

    @DisplayName("비밀번호를 수정한다.")
    @Test
    void update() {
        Password password = Password.from("password1234");
        String newPassword = "password1111";

        Password update = password.update(newPassword);
        Password from = Password.from(newPassword);
        assertThat(update).isEqualTo(from);
    }
}
