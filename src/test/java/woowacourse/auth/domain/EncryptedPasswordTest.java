package woowacourse.auth.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
class EncryptedPasswordTest {

    @Test
    void hasSamePassword는_원본_비밀번호에_대해_참_반환() {
        String 원본_비밀번호 = "password!1";
        EncryptedPassword 암호화된_비밀번호 = new Password(원본_비밀번호).toEncrypted();

        boolean 같은_비밀번호 = 암호화된_비밀번호.hasSamePassword(원본_비밀번호);

        assertThat(같은_비밀번호).isTrue();
    }

    @Test
    void hasSamePassword는_원본_비밀번호와_다른_값에_대해_거짓_반환() {
        String 원본_비밀번호 = "password!1";
        String 다른_비밀번호 = "password!2";
        EncryptedPassword 암호화된_비밀번호 = new Password(원본_비밀번호).toEncrypted();

        boolean 같은_비밀번호 = 암호화된_비밀번호.hasSamePassword(다른_비밀번호);

        assertThat(같은_비밀번호).isFalse();
    }

    @Test
    void hasSamePassword는_제대로_암호화되지_않은_인스턴스에서_실행되면_예외발생() {
        String 원본_비밀번호 = "password!1";

        EncryptedPassword 암호화되지_않은_비밀번호 = new EncryptedPassword(원본_비밀번호);

        assertThatThrownBy(() -> 암호화되지_않은_비밀번호.hasSamePassword(원본_비밀번호))
                .isInstanceOf(IllegalStateException.class);
    }
}
