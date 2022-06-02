package woowacourse.auth.support;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import woowacourse.shoppingcart.domain.customer.Password;
import woowacourse.shoppingcart.domain.customer.PasswordEncoder;
import woowacourse.shoppingcart.domain.customer.PlainPassword;

class EncryptPasswordEncoderTest {

    private final PasswordEncoder passwordEncoder = new EncryptPasswordEncoder();

    @Test
    @DisplayName("비밀번호를 인코딩한다.")
    void encode() {
        PlainPassword plainPassword = new PlainPassword("password123");

        assertThat(passwordEncoder.encode(plainPassword)).isNotNull();
    }

    @ParameterizedTest
    @CsvSource(value = {"password123,true", "123password,false"})
    @DisplayName("패스워드가 동일한지 확인한다.")
    void isMatchPassword(String inputPassword, boolean expected) {
        PlainPassword plainPassword = new PlainPassword("password123");
        Password encodedPassword = passwordEncoder.encode(new PlainPassword(inputPassword));

        assertThat(passwordEncoder.isMatchPassword(encodedPassword, plainPassword)).isEqualTo(expected);
    }
}
