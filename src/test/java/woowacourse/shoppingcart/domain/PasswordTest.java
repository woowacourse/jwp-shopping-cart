package woowacourse.shoppingcart.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import woowacourse.exception.PasswordLengthException;

public class PasswordTest {

    @DisplayName("비밀번호가 8자리 미만이면 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"1", "123", "23456", "1234567"})
    void passwordWithException(String password) {
        assertThatThrownBy(() -> new Password(password))
                .isInstanceOf(PasswordLengthException.class);
    }

    @DisplayName("비밀번호가 8자리 이상이면 비밀번호 객체가 생성된다.")
    @ParameterizedTest
    @ValueSource(strings = {"12345678", "123456789", "yooyoungwoo", "woowahan"})
    void password(String password) {
        assertThat(new Password(password).getPassword()).isEqualTo(password);
    }
}
