package woowacourse.shoppingcart.domain.customer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class CustomerTest {

    @DisplayName("비밀번호가 일치하지 않으면 예외가 발생한다.")
    @Test
    void validatePassword_not_matching() {
        Customer customer = Customer.of("forky123", "forky!1234", "forky", 26);
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> customer.validatePassword("kei!1234"))
                .withMessageContaining("일치");
    }
}