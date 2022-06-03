package woowacourse.shoppingcart.domain;

import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import woowacourse.shoppingcart.exception.InvalidPasswordException;

public class CustomerTest {

    @Test
    @DisplayName("비밀번호가 일치하는지 확인한다.")
    void validatePassword() {
        // given
        Customer customer = new Customer(1L, "레넌", "rennon@woowa.com", "123456");

        // when & then
        assertThatCode(() -> customer.validatePassword("123456"))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("비밀번호가 일치하지 않으면 예외가 발생한다..")
    void validatePasswordThrowException() {
        // given
        Customer customer = new Customer(1L, "레넌", "rennon@woowa.com", "123456");

        // when & then
        assertThatCode(() -> customer.validatePassword("12345"))
                .isInstanceOf(InvalidPasswordException.class)
                .hasMessage("비밀번호가 틀렸습니다.");
    }
}
