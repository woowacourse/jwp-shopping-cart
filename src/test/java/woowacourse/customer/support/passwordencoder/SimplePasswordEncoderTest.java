package woowacourse.customer.support.passwordencoder;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SimplePasswordEncoderTest {

    private final PasswordEncoder passwordEncoder = new SimplePasswordEncoder();

    private final String originalPassword = "original1234";
    private final String wrongPassword = "wrong1234";

    @DisplayName("암호화된 비밀번호와 같은 비밀번호인지 확인한다.")
    @Test
    void matches() {
        final String encodedPassword = passwordEncoder.encode(originalPassword);

        assertThat(passwordEncoder.matches(originalPassword, encodedPassword)).isTrue();
    }

    @DisplayName("암호화된 비밀번호와 같지 않으면 False를 반환한다.")
    @Test
    void matchesFalse() {
        final String encodedPassword = passwordEncoder.encode(originalPassword);

        assertThat(passwordEncoder.matches(wrongPassword, encodedPassword)).isFalse();
    }
}