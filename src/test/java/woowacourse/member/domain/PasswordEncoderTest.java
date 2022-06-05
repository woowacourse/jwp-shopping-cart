package woowacourse.member.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class PasswordEncoderTest {

    @DisplayName("객체 생성시 비밀번호를 암호화한다.")
    @Test
    void encrypt() {
        String password = "Wooteco123!";
        String encryptedPassword1 = PasswordEncoder.encrypt(password);
        String encryptedPassword2 = PasswordEncoder.encrypt(password);

        assertAll(
                () -> assertThat(encryptedPassword1).isEqualTo(encryptedPassword2),
                () -> assertThat(encryptedPassword1).isNotEqualTo(password)
        );
    }
}
