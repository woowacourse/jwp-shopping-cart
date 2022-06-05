package woowacourse.shoppingcart.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import woowacourse.shoppingcart.exception.InvalidEmailException;
import woowacourse.shoppingcart.exception.InvalidUsernameException;

public class CustomerTest {

    @Test
    @DisplayName("회원을 생성할 수 있다.")
    void construct() {
        // given
        String username = "레넌";
        String email = "rennon@woowa.com";
        String password = "123456";

        // when & then
        assertThatCode(() -> new Customer(username, email, password))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("회원이름이 적절하지 않으면 생성할 수 없다.")
    void constructThrowInvalidUsernameException() {
        assertThatThrownBy(() -> new Customer("레 넌", "rennon@woowa.com", "123456"))
                .isInstanceOf(InvalidUsernameException.class)
                .hasMessage("회원 이름에는 공백이 들어갈 수 없습니다.");
    }

    @Test
    @DisplayName("이메일이 적절하지 않으면 생성할 수 없다.")
    void constructThrowInvalidEmailException() {
        assertThatThrownBy(() -> new Customer("레넌", "rennonwoowa.com", "123456"))
                .isInstanceOf(InvalidEmailException.class)
                .hasMessage("이메일 형식이 올바르지 않습니다.");
    }
}
