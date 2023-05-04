package cart.utils;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class CaesarCipherTest {

    @Test
    void 비밀번호를_암호화_한다() {
        final String actualPassword = "abcd";
        final String expectedEncryptedPassword = "defg";

        assertThat(CaesarCipher.encrypt(actualPassword)).isEqualTo(expectedEncryptedPassword);
    }

    @Test
    void 비밀번호를_복호화_한다() {
        final String encryptedPassword = "Defg";
        final String expectedDecryptedPassword = "Abcd";

        assertThat(CaesarCipher.decrypt(encryptedPassword)).isEqualTo(expectedDecryptedPassword);
    }

    @Test
    void 비밀번호를_암호화한뒤에_복호화하면_똑같다() {
        final String randomPassword = "4324fsdnkldsnal4";

        assertThat(CaesarCipher
                .decrypt(CaesarCipher.encrypt(randomPassword)))
                .isEqualTo(randomPassword);
    }

}
