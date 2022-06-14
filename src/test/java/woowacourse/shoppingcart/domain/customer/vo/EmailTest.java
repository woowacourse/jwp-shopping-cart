package woowacourse.shoppingcart.domain.customer.vo;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class EmailTest {

    @DisplayName("이메일 사이즈 테스트")
    @Test
    void validateSize() {
        assertThatThrownBy(() -> Email.from("")).isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Invalid Email");
    }

    @DisplayName("이메일 패턴 테스트")
    @Test
    void validatePattern() {
        assertThatThrownBy(() -> Email.from("email")).isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Invalid Email");
    }

    @DisplayName("이메일 정상 생성 테스트")
    @Test
    void create() {
        assertDoesNotThrow(() -> Email.from("email@email.com"));
    }
}
