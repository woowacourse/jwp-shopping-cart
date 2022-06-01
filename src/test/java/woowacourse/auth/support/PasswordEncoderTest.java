package woowacourse.auth.support;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PasswordEncoderTest {

    @DisplayName("패스워드를 암호화 한다.")
    @Test
    void encode() {
        String password = "sdakdasf1234";

        PasswordEncoder passwordEncoder = new PasswordEncoder();
        String encodedPassword = passwordEncoder.encode(password);
        System.out.println(encodedPassword);

        assertThat(encodedPassword).hasSize(64);
    }
}
