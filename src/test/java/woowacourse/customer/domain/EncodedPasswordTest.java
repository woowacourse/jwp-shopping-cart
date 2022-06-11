package woowacourse.customer.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import woowacourse.auth.exception.InvalidLoginException;
import woowacourse.customer.support.passwordencoder.PasswordEncoder;
import woowacourse.customer.support.passwordencoder.SimplePasswordEncoder;

class EncodedPasswordTest {

    private PasswordEncoder passwordEncoder;

    private final String originalPassword = "original1234";
    private final String newPassword = "newnew1234";
    private final String wrongPassword = "wrong1234";

    @BeforeEach
    void setUp() {
        passwordEncoder = new SimplePasswordEncoder();
    }

    @DisplayName("비밀번호를 수정한다.")
    @Test
    void update() {
        final EncodedPassword password = new EncodedPassword(originalPassword);
        final EncodedPassword updatePassword = password.update(newPassword);

        assertThat(updatePassword.getValue()).isEqualTo(newPassword);
    }

    @DisplayName("비밀번호가 일치하면 예외를 반환하지 않아야 한다.")
    @Test
    void matchPassword() {
        final String encodedValue = passwordEncoder.encode(originalPassword);
        final EncodedPassword encodedPassword = new EncodedPassword(encodedValue);

        assertDoesNotThrow(() -> encodedPassword.matches(passwordEncoder, originalPassword));
    }

    @DisplayName("비밀번호가 일치하지 않으면 예외를 반환해야 한다.")
    @Test
    void matchWrongPassword() {
        final String encodedValue = passwordEncoder.encode(originalPassword);
        final EncodedPassword encodedPassword = new EncodedPassword(encodedValue);

        assertThatThrownBy(() -> encodedPassword.matches(passwordEncoder, wrongPassword))
            .hasMessage("로그인 정보가 시스템에 있는 계정과 일치하지 않습니다.")
            .isInstanceOf(InvalidLoginException.class);
    }
}