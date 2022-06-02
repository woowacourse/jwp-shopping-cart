package woowacourse.shoppingcart.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import woowacourse.auth.support.PasswordEncoder;

class PasswordEncoderTest {

    @DisplayName("비밀번호 암호화")
    @Test
    void encryptPassword() {
        String rawPassword = "tonic1234";

        String encryptPassword = PasswordEncoder.encrypt(rawPassword);

        assertThat(encryptPassword).isEqualTo(PasswordEncoder.encrypt(rawPassword));
    }
}
