package woowacourse.shoppingcart.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class PasswordTest {

    @DisplayName("비밀번호 형식이 정상적일 경우 생성한다.")
    @ParameterizedTest
    @ValueSource(strings = {"12345678aA!", "test123A@", "test234234aaA$"})
    void createPassword(String password) {
        Password actual = new Password(password);

        assertThat(actual.getValue()).isEqualTo(password);
    }

    @DisplayName("비밀번호 형식이 유효하지 않을 경우 예외발생.")
    @ParameterizedTest
    @ValueSource(strings = {"sdf!@#!@#", "123456789", "!@$!23@$"})
    void throwExceptionValidate(String password) {
        assertThatThrownBy(() -> new Password(password))
            .isInstanceOf(IllegalArgumentException.class);
    }
}
