package woowacourse.shoppingcart.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import woowacourse.shoppingcart.dto.SignUpRequest;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CustomerServiceTest {

    private final CustomerService customerService = new CustomerService();

    @DisplayName("사용자 이름에 null 을 입력하면 안된다.")
    @Test
    void signUpUsernameNullException() {
        // given
        SignUpRequest signUpRequest = new SignUpRequest(null,"1234");

        // when & than
        assertThatThrownBy(() -> customerService.signUp(signUpRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("사용자 이름을 입력해주세요.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    @DisplayName("사용자 이름에 빈값을 입력하면 안된다.")
    void signUpUsernameBlankException(String username) {
        // given
        SignUpRequest signUpRequest = new SignUpRequest(username,"1234");

        // when & than
        assertThatThrownBy(() -> customerService.signUp(signUpRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("사용자 이름을 입력해주세요.");
    }

    @DisplayName("비밀번호에 null 을 입력하면 안된다.")
    @Test
    void signUpPasswordNullException() {
        // given
        SignUpRequest signUpRequest = new SignUpRequest("username", null);

        // when & than
        assertThatThrownBy(() -> customerService.signUp(signUpRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("비밀번호를 입력해주세요.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    @DisplayName("비밀번호에 빈값을 입력하면 안된다.")
    void signUpPasswordBlankException(String password) {
        // given
        SignUpRequest signUpRequest = new SignUpRequest("username", password);

        // when & than
        assertThatThrownBy(() -> customerService.signUp(signUpRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("비밀번호를 입력해주세요.");
    }
}
