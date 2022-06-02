package woowacourse.shoppingcart.domain.customer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import woowacourse.shoppingcart.exception.PasswordMisMatchException;
import woowacourse.shoppingcart.support.passwordencoder.PasswordEncoder;
import woowacourse.shoppingcart.support.passwordencoder.SimplePasswordEncoder;

class EncodedPasswordTest {

    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        passwordEncoder = new SimplePasswordEncoder();
    }

    @DisplayName("비밀번호를 수정한다.")
    @Test
    void update() {
        final EncodedPassword password = new EncodedPassword("password1234");
        final String newPassword = "password1111";
        final EncodedPassword updatePassword = password.update(newPassword);

        assertThat(updatePassword.getValue()).isEqualTo(newPassword);
    }

    @DisplayName("비밀번호가 일치하면 예외를 반환하지 않아야 한다.")
    @Test
    void matchPassword() {
        final String password = passwordEncoder.encode("12341234");
        final EncodedPassword encodedPassword = new EncodedPassword(password);
        assertDoesNotThrow(() -> encodedPassword.matches(passwordEncoder, "12341234"));
    }

    @DisplayName("비밀번호가 일치하지 않으면 예외를 반환해야 한다.")
    @Test
    void matchWrongPassword() {
        final EncodedPassword encodedPassword = new EncodedPassword("12341234");
        assertThatThrownBy(() -> encodedPassword.matches(passwordEncoder, "43214321"))
            .hasMessage("비밀번호가 일치하지 않습니다.")
            .isInstanceOf(PasswordMisMatchException.class);
    }
}