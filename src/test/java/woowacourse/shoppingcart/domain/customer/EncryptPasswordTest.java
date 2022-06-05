package woowacourse.shoppingcart.domain.customer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static woowacourse.fixture.PasswordFixture.plainBasicPassword;
import static woowacourse.fixture.PasswordFixture.plainReversePassword;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class EncryptPasswordTest {

    @DisplayName("비밀번호가 일치하는지를 반환한다.")
    @Test
    void matches() {
        final EncryptPassword encryptPassword = new EncryptPassword(plainBasicPassword);
        PasswordEncryptor passwordEncryptor = new PasswordTestEncryptor();

        assertAll(
                () -> assertThat(encryptPassword.matches(passwordEncryptor, plainBasicPassword)).isTrue(),
                () -> assertThat(encryptPassword.matches(passwordEncryptor, plainReversePassword)).isFalse()
        );
    }
}
