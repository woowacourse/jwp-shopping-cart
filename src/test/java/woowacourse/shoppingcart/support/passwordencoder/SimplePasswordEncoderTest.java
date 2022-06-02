package woowacourse.shoppingcart.support.passwordencoder;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SimplePasswordEncoderTest {

    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        passwordEncoder = new SimplePasswordEncoder();
    }

    @DisplayName("암호화된 비밀번호와 같은 비밀번호인지 확인한다.")
    @Test
    void matches() {
        final String rawPassword = "password1234";
        final String encodedPassword = passwordEncoder.encode(rawPassword);

        assertThat(passwordEncoder.matches(rawPassword, encodedPassword)).isTrue();
    }

    @DisplayName("암호화된 비밀번호와 같지 않으면 False를 반환한다.")
    @Test
    void matchesFalse() {
        final String rawPassword = "password1234";
        final String encodedPassword = passwordEncoder.encode(rawPassword);

        assertThat(passwordEncoder.matches("ppaa1122", encodedPassword)).isFalse();
    }
}