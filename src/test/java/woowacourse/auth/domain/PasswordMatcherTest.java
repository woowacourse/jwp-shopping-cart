package woowacourse.auth.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import woowacourse.shoppingcart.domain.customer.BcryptPasswordEncryptor;
import woowacourse.shoppingcart.domain.customer.PasswordEncryptor;

class PasswordMatcherTest {

    private final PasswordEncryptor encryptor = new BcryptPasswordEncryptor();
    private final PasswordMatcher matcher = new BcryptPasswordMatcher();

    @Test
    @DisplayName("암호화된 패스워드가 평문 패스워드와 일치하는지 검사한다.")
    public void isMatch() {
        // given
        String input = "helloworld";
        final String encoded = encryptor.encrypt(input);
        // when
        final boolean isMatch = matcher.isMatch(input, encoded);

        // then
        assertThat(isMatch).isTrue();
    }

}