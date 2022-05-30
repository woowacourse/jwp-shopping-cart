package woowacourse.shoppingcart.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CustomerTest {

    @DisplayName("사용자 이름에 null 을 입력하면 안된다.")
    @Test
    void usernameNullException() {
        // when & than
        assertThatThrownBy(() -> new Customer(null, "1234"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("사용자 이름을 입력해주세요.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    @DisplayName("사용자 이름에 빈값을 입력하면 안된다.")
    void usernameBlankException(String username) {
        // when & than
        assertThatThrownBy(() -> new Customer(username, "1234"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("사용자 이름을 입력해주세요.");
    }

    @DisplayName("비밀번호에 null 을 입력하면 안된다.")
    @Test
    void passwordNullException() {
        // when & than
        assertThatThrownBy(() -> new Customer("username", null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("비밀번호를 입력해주세요.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    @DisplayName("비밀번호에 빈값을 입력하면 안된다.")
    void passwordBlankException(String password) {
        // when & than
        assertThatThrownBy(() -> new Customer("username", password))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("비밀번호를 입력해주세요.");
    }
}
