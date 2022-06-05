package woowacourse.shoppingcart.domain.customer;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.fixture.PasswordFixture.plainBasicPassword;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PasswordSecureHashEncryptorTest {

    @DisplayName("비밀번호를 암호화한다.")
    @Test
    void encodePassword() {
        PasswordEncryptor passwordEncryptor = new PasswordSecureHashEncryptor();

        assertThat(passwordEncryptor.encode(plainBasicPassword)).isNotBlank();
    }

    @DisplayName("비밀번호가 맞는지 검증한다.")
    @Test
    void matchPassword() {
        PasswordEncryptor passwordEncryptor = new PasswordSecureHashEncryptor();
        String encryptPassword = passwordEncryptor.encode(plainBasicPassword);

        assertThat(passwordEncryptor.matches(plainBasicPassword, encryptPassword)).isTrue();
    }
}
