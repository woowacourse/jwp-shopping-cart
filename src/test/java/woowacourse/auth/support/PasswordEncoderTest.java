package woowacourse.auth.support;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import woowacourse.shoppingcart.domain.customer.EncodedPassword;
import woowacourse.shoppingcart.domain.customer.UnEncodedPassword;

class PasswordEncoderTest {

    @DisplayName("패스워드를 암호화 한다.")
    @Test
    void encode() {
        UnEncodedPassword password = new UnEncodedPassword("sdakdasf1234");

        PasswordEncoder passwordEncoder = new PasswordEncoder();
        EncodedPassword encodedPassword = passwordEncoder.encode(password);

        assertThat(encodedPassword.getValue()).hasSize(64);
    }
}
