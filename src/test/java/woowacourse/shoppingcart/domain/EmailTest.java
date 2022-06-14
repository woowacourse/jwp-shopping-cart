package woowacourse.shoppingcart.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import woowacourse.shoppingcart.domain.customer.Email;

public class EmailTest {

    @DisplayName("이메일 8자 이상 50자 이하가 아니면 예외를 발생시킨다.")
    @Test
    void validateLength() {
        assertThatThrownBy(() -> new Email("12@ab.c"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("이메일 형식이 아니면 예외를 발생시킨다.")
    @Test
    void validateEmailForm() {
        assertThatThrownBy(() -> new Email("12345678"))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
