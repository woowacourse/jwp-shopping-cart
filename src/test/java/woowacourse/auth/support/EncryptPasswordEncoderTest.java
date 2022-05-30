package woowacourse.auth.support;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class EncryptPasswordEncoderTest {

    @Test
    @DisplayName("비밀번호를 인코딩한다.")
    void encode() {
        PasswordEncoder passwordEncoder = new EncryptPasswordEncoder();
        String password = "password";

        assertThat(passwordEncoder.encode(password)).isNotNull();
    }
}
