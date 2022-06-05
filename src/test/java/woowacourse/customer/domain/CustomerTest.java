package woowacourse.customer.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import woowacourse.customer.domain.Customer;
import woowacourse.customer.domain.PhoneNumber;
import woowacourse.customer.domain.Username;
import woowacourse.customer.support.passwordencoder.PasswordEncoder;
import woowacourse.customer.support.passwordencoder.SimplePasswordEncoder;

class CustomerTest {

    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        passwordEncoder = new SimplePasswordEncoder();
    }

    private final String username = "username";
    private final String password = "password";
    private final String phoneNumber = "01012341234";
    private final String address = "인천 서구 검단로";

    @DisplayName("Customer를 생성해야합니다.")
    @Test
    void createCustomer() {
        final String encodedPassword = passwordEncoder.encode(password);
        final Customer customer = Customer.of(username, encodedPassword, phoneNumber, address);

        assertAll(
            () -> assertThat(customer.getUsername()).isEqualTo(new Username(username)),
            () -> assertThat(passwordEncoder.matches(password, customer.getPassword().getValue())).isTrue(),
            () -> assertThat(customer.getPhoneNumber()).isEqualTo(new PhoneNumber(phoneNumber)),
            () -> assertThat(customer.getAddress()).isEqualTo(address)
        );
    }

    @DisplayName("phoneNumber를 수정한다.")
    @Test
    void updatePhoneNumber() {
        final Customer customer = Customer.of(username, password, phoneNumber, address);
        final String newPhoneNumber = "01011112222";
        customer.updatePhoneNumber(newPhoneNumber);

        assertThat(customer.getPhoneNumber().getValue()).isEqualTo(newPhoneNumber);
    }

    @DisplayName("address를 수정한다.")
    @Test
    void updateAddress() {
        final Customer customer = Customer.of(username, password, phoneNumber, address);
        final String newAddress = "서울시 강남구";
        customer.updateAddress(newAddress);

        assertThat(customer.getAddress()).isEqualTo(newAddress);
    }

    @DisplayName("password를 수정한다.")
    @Test
    void updatePassword() {
        final Customer customer = Customer.of(username, password, phoneNumber, address);
        final String newPassword = "password1111";
        customer.updatePassword(newPassword);

        assertThat(customer.getPassword().getValue()).isEqualTo(newPassword);
    }
}
