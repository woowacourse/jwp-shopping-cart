package woowacourse.shoppingcart.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

class PasswordEncryptorTest {

    private static final int ENCRYPTED_SIZE = 60;
    private final PasswordEncryptor encryptor = new BcryptPasswordEncryptor(new BCryptPasswordEncoder());

    @Test
    @DisplayName("평문을 암호화한다.")
    public void encryptPassword() {
        // given
        String input = "password";

        // when
        final String encrypted = encryptor.encrypt(input);

        // then
        assertAll(
            () -> assertThat(encrypted.length()).isEqualTo(ENCRYPTED_SIZE),
            () -> assertThat(encrypted).isNotEqualTo(input)
        );

    }

}