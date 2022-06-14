package woowacourse.shoppingcart.domain.customer.vo;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PasswordTest {

    @DisplayName("비밀번호 사이즈 테스트")
    @Test
    void validateSize() {
        assertThatThrownBy(() -> Password.from("pass")).isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Invalid Password");
    }

    @DisplayName("비밀번호 패턴 테스트")
    @Test
    void validatePattern() {
        assertThatThrownBy(() -> Password.from("password123")).isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Invalid Password");
    }

    @DisplayName("비밀번호 정상 생성 테스트")
    @Test
    void create() {
        assertDoesNotThrow(() -> Password.from("password1!"));
    }

    @DisplayName("동일한 이메일이 아닌 경우 예외 발생")
    @Test
    void checkSame() {
        Password password = Password.from("password1!");

        assertThatThrownBy(() -> password.checkPasswordIsSame("password2!")).isInstanceOf(
            IllegalArgumentException.class).hasMessage("고객 정보가 일치하지 않습니다.");
    }
}
