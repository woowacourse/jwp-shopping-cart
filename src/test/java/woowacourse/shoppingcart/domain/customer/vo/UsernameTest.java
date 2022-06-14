package woowacourse.shoppingcart.domain.customer.vo;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UsernameTest {

    @DisplayName("빈 문자열 닉네임 테스트")
    @Test
    void validateBlank() {
        assertThatThrownBy(() -> Username.from("")).isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Invalid Username");
    }

    @DisplayName("닉네임 사이즈가 10보다 클 경우 테스트")
    @Test
    void validateMaxSize() {
        assertThatThrownBy(() -> Username.from("12345678901")).isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Invalid Username");
    }
}
