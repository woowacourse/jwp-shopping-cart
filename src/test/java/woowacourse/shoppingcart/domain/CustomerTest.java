package woowacourse.shoppingcart.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CustomerTest {

    @DisplayName("아이디에 null 을 입력하면 안된다.")
    @Test
    void userIdNullException() {
        // when & than
        assertThatThrownBy(() -> new Customer(null, "유콩", "1234"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("아이디를 입력해주세요.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    @DisplayName("아이디에 빈값을 입력하면 안된다.")
    void userIdBlankException(String userId) {
        // when & than
        assertThatThrownBy(() -> new Customer(userId, "유콩", "1234"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("아이디를 입력해주세요.");
    }

    @DisplayName("닉네임에 null 을 입력하면 안된다.")
    @Test
    void nicknameNullException() {
        // when & than
        assertThatThrownBy(() -> new Customer("userId", null, "1234"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("닉네임을 입력해주세요.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    @DisplayName("닉네임에 빈값을 입력하면 안된다.")
    void nicknameBlankException(String nickname) {
        // when & than
        assertThatThrownBy(() -> new Customer("userId", nickname, "1234"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("닉네임을 입력해주세요.");
    }

    @DisplayName("비밀번호에 null 을 입력하면 안된다.")
    @Test
    void passwordNullException() {
        // when & than
        assertThatThrownBy(() -> new Customer("userId", "유콩", null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("비밀번호를 입력해주세요.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    @DisplayName("비밀번호에 빈값을 입력하면 안된다.")
    void passwordBlankException(String password) {
        // when & than
        assertThatThrownBy(() -> new Customer("userId", "유콩", password))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("비밀번호를 입력해주세요.");
    }
}
