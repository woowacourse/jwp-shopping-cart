package woowacourse.auth.support;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class EncryptionTest {

    private Encryption encryption = new Encryption();

    @DisplayName("암호회 된 문장이 원본과 일치하면 true를 반환한다.")
    @Test
    void isSame_true() {
        String plainText = "password123";
        String encryptedText = encryption.encrypt(plainText);

        assertThat(encryption.isSame(encryptedText, plainText)).isTrue();
    }

    @DisplayName("암호회 된 문장이 원본과 일치하지 않으면 false를 반환한다")
    @Test
    void isSame_false() {
        String plainText = "password123";
        String encryptedText = encryption.encrypt(plainText);

        assertThat(encryption.isSame(encryptedText, "123password")).isFalse();
    }
}
