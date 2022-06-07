package woowacourse.shoppingcart.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class LoginIdTest {

    @DisplayName("로그인 아이디를 정상적으로 생성한다.")
    @ParameterizedTest
    @ValueSource(strings = {"abc@abc.com", "seungpapang@gmail.com", "angie@hanmail.net"})
    void createLoginId(String email) {
        LoginId loginId = new LoginId(email);

        assertThat(loginId.getValue()).isEqualTo(email);
    }

    @DisplayName("로그인 아이디 형식이 유효하지 않을 경우 예외발생.")
    @ParameterizedTest
    @ValueSource(strings = {".@.", "!@sdf.com", "123123@sdfsdf"})
    void throwExceptionValidate(String email) {
        assertThatThrownBy(() -> new LoginId(email))
            .isInstanceOf(IllegalArgumentException.class);
    }
}
