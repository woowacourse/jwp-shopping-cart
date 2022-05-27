package woowacourse.shoppingcart.domain.customer;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CustomerTest {

    @DisplayName("Customer를 생성해야합니다.")
    @Test
    void createCustomer() {
        String username = "dongho108";
        String password = "password1234";
        String phoneNumber = "01012341234";
        String address = "인천 서구 검단로";

        Customer customer = Customer.of(username, password, phoneNumber, address);
        assertAll(
            () -> assertThat(customer.getUsername()).isEqualTo(new Username(username)),
            () -> assertThat(customer.getPassword()).isEqualTo(new Password(password)),
            () -> assertThat(customer.getPhoneNumber()).isEqualTo(new PhoneNumber(phoneNumber)),
            () -> assertThat(customer.getAddress()).isEqualTo(address)
        );
    }
}