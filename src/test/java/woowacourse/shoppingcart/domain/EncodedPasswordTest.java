package woowacourse.shoppingcart.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import woowacourse.shoppingcart.util.PasswordEncryptor;

class EncodedPasswordTest {

    @DisplayName("암호화된 비밀번호가 정상적으로 생성되는지 확인한다.")
    @Test
    void create() {
        // given
        final String password = PasswordEncryptor.encrypt("1234");

        // when, then
        assertThatCode(() -> new EncodedPassword(password))
                .doesNotThrowAnyException();
    }

    @DisplayName("평문으로 암호화된 비밀번호를 생성할 경우 예외가 발생한다.")
    @Test
    void create_withPlainPassword_throwsException() {
        // given
        final String password = "12345678";

        // when, then
        assertThatThrownBy(() -> new EncodedPassword(password))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("암호화된 비밀번호가 입력되어야 합니다.");
    }

    @DisplayName("패스워드 일치 여부를 반환한다.")
    @ParameterizedTest
    @CsvSource(value = {"1234,true", "5678,false"})
    void isSamePassword(final String password, final boolean expected) {
        // given
        final EncodedPassword encodedPassword = new EncodedPassword(PasswordEncryptor.encrypt("1234"));

        // when, then
        assertThat(encodedPassword.isSamePassword(password)).isEqualTo(expected);
    }
}
