package woowacourse.shoppingcart.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import woowacourse.shoppingcart.dto.SignUpRequest;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CustomerServiceTest {

    private final CustomerService customerService = new CustomerService();

    @DisplayName("아이디에 null 을 입력하면 안된다.")
    @Test
    void signUpUserIdNullException() {
        // given
        SignUpRequest signUpRequest = new SignUpRequest(null, "유콩", "1234");

        // when & than
        assertThatThrownBy(() -> customerService.signUp(signUpRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("아이디를 입력해주세요.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    @DisplayName("아이디에 빈값을 입력하면 안된다.")
    void signUpUserIdBlankException(String userId) {
        // given
        SignUpRequest signUpRequest = new SignUpRequest(userId, "유콩", "1234");

        // when & than
        assertThatThrownBy(() -> customerService.signUp(signUpRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("아이디를 입력해주세요.");
    }

    @DisplayName("닉네임에 null 을 입력하면 안된다.")
    @Test
    void signUpNicknamedNullException() {
        // given
        SignUpRequest signUpRequest = new SignUpRequest("username", null, "1234");

        // when & than
        assertThatThrownBy(() -> customerService.signUp(signUpRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("닉네임을 입력해주세요.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    @DisplayName("닉네임에 빈값을 입력하면 안된다.")
    void signUpNicknameBlankException(String nickname) {
        // given
        SignUpRequest signUpRequest = new SignUpRequest("username", nickname, "1234");

        // when & than
        assertThatThrownBy(() -> customerService.signUp(signUpRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("닉네임을 입력해주세요.");
    }

    @DisplayName("비밀번호에 null 을 입력하면 안된다.")
    @Test
    void signUpPasswordNullException() {
        // given
        SignUpRequest signUpRequest = new SignUpRequest("username", "유콩", null);

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
        SignUpRequest signUpRequest = new SignUpRequest("username", "유콩", password);

        // when & than
        assertThatThrownBy(() -> customerService.signUp(signUpRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("비밀번호를 입력해주세요.");
    }
}
