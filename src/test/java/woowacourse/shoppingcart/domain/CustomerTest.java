package woowacourse.shoppingcart.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.exception.InvalidCustomerPropertyException;

public class CustomerTest {

    @ParameterizedTest
    @DisplayName("잘못된 email 인 경우 예외를 던진다.")
    @ValueSource(strings = {"sojukangroma", "sojukang @naver.com", "a@b.c"})
    @NullSource
    void InvalidEmail(String email) {
        //when, then
        Assertions.assertThatThrownBy(
                () -> new Customer(email, "roma", "12345678"))
            .isInstanceOf(InvalidCustomerPropertyException.class);
    }

    @ParameterizedTest
    @DisplayName("잘못된 username 인 경우 예외를 던진다.")
    @ValueSource(strings = {"", "12345678901", "ab c"})
    @NullSource
    void InvalidUsername(String username) {
        //when, then
        Assertions.assertThatThrownBy(
                () -> new Customer("sojukang@naver.com", username, "12345678"))
            .isInstanceOf(InvalidCustomerPropertyException.class);
    }

    @ParameterizedTest
    @DisplayName("잘못된 password 인 경우 예외를 던진다.")
    @ValueSource(strings = {"1234567", "123456789012345678901", "1234567 8"})
    @NullSource
    void InvalidPassword(String password) {
        //when, then
        Assertions.assertThatThrownBy(
                () -> new Customer("sojukang@naver.com", "roma", password))
            .isInstanceOf(InvalidCustomerPropertyException.class);
    }
}
