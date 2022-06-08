package study;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCrypt;

@SuppressWarnings("NonAsciiCharacters")
class BcryptTest {

    @DisplayName("checkpw는 해쉬값의 솔트를 복원하여, 원본 비밀번호 암호화에 활용하고, 두 해쉬값이 동일한지를 확인한다")
    @Test
    void checkpwMatch() {
        String 비밀번호 = "a!1";

        String 암호화된_비밀번호 = BCrypt.hashpw(비밀번호, BCrypt.gensalt());
        boolean samePassword = BCrypt.checkpw(비밀번호, 암호화된_비밀번호);

        assertThat(samePassword).isTrue();
    }

    @DisplayName("checkpw는 다른 비밀번호에 대해 거짓을 반환")
    @Test
    void checkpwNotMatch() {
        String 비밀번호 = "a!1";
        String 다른_비밀번호 = "a!2";
        String 암호화된_비밀번호 = BCrypt.hashpw(비밀번호, BCrypt.gensalt());

        boolean samePassword = BCrypt.checkpw(다른_비밀번호, 암호화된_비밀번호);

        assertThat(samePassword).isFalse();
    }

    @DisplayName("hashpw는 동일한 입력값과 솔트에 대해 동일한 해쉬값을 생성한다.")
    @Test
    void hashpwWithSameSalt() {
        String 유효한_비밀번호 = "a!1";
        String 솔트 = BCrypt.gensalt();

        String 암호화된_비밀번호 = BCrypt.hashpw(유효한_비밀번호, 솔트);
        String 똑같이_암호화된_비밀번호 = BCrypt.hashpw(유효한_비밀번호, 솔트);

        assertThat(암호화된_비밀번호).isEqualTo(똑같이_암호화된_비밀번호);
    }

    @DisplayName("hashpw는 동일한 입력값과 다른 솔트에 대해 다른 해쉬값을 생성한다.")
    @Test
    void hashpwWithDifferentSalt() {
        String 유효한_비밀번호 = "a!1";
        String 솔트 = "$2a$10$7CJMWcz1dx5tmazKr88f0u";
        String 다른_솔트 =  "$2a$10$OFMhanEs0njGIAEQKtfvC.";

        String 암호화된_비밀번호 = BCrypt.hashpw(유효한_비밀번호, 솔트);
        String 다른_솔트로_암호화된_비밀번호 = BCrypt.hashpw(유효한_비밀번호, 다른_솔트);

        assertThat(암호화된_비밀번호).isNotEqualTo(다른_솔트로_암호화된_비밀번호);
    }
}
