package woowacourse.shoppingcart.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import woowacourse.exception.badRequest.PasswordInvalidException;

public class PasswordTest {

    @DisplayName("비밀번호가 8자리 미만이면 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"1", "123", "23456", "1234567"})
    void passwordWithException(String password) {
        assertThatThrownBy(() -> Password.planePassword(password))
                .isInstanceOf(PasswordInvalidException.class);
    }

    @DisplayName("비밀번호가 8자리 이상이면 비밀번호 객체가 생성되고 암호화된다.")
    @ParameterizedTest
    @ValueSource(strings = {"*a12345678", "*h123456789", "h*123456", "*ryu12345"})
    void password(String password) {
        final Password encodedPassword = Password.planePassword(password);
        assertThat(encodedPassword.isMatches(password)).isTrue();
    }
}
