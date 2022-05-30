package woowacourse.shoppingcart.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CustomerTest {

    @Test
    @DisplayName("이메일 형식이 맞지 않는 경우 예외가 발생한다.")
    void validateEmail() {
        // given
        String email = "beomWhale";
        String nickname = "범고래";
        String password = "Password12345!";

        // when
        assertThatThrownBy(() -> new Customer(email, nickname, password)).isInstanceOf(IllegalArgumentException.class)
            .hasMessage("이메일 형식이 맞지 않습니다.");
    }
}
