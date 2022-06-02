package woowacourse.auth.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

class PasswordMatcherTest {

    private final PasswordEncoder encoder = new BCryptPasswordEncoder();
    private final PasswordMatcher matcher = new BcryptPasswordMatcher(encoder);

    @Test
    @DisplayName("암호화된 패스워드가 평문 패스워드와 일치하는지 검사한다.")
    public void isMatch() {
        // given
        String input = "helloworld";
        final String encoded = encoder.encode(input);
        // when
        final boolean isMatch = matcher.isMatch(input, encoded);

        // then
        assertThat(isMatch).isTrue();
    }

}