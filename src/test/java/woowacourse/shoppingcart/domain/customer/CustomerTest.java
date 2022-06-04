package woowacourse.shoppingcart.domain.customer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNoException;

class CustomerTest {

    @DisplayName("비밀번호가 일치하지 않으면 예외가 발생한다.")
    @Test
    void validatePassword_not_matching() {
        Customer customer = Customer.of("forky123", "forky!1234", "forky", 26);
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> customer.validatePassword("kei!1234"))
                .withMessageContaining("일치");
    }

    @DisplayName("비밀번호가 일치하면 예외가 발생하지 않는다.")
    @Test
    void validatePassword() {
        Customer customer = Customer.of("forky123", "forky!1234", "forky", 26);
        assertThatNoException()
                .isThrownBy(() -> customer.validatePassword("forky!1234"));
    }

    @DisplayName("비밀번호를 변경할 수 있다.")
    @Test
    void updatePassword() {
        Customer customer = Customer.of("forky123", "forky!1234", "forky", 26);
        String expected = "forky!4321";
        Customer updated = customer.updatePassword(expected);
        assertThat(updated.getPassword()).isEqualTo(expected);
    }
}
