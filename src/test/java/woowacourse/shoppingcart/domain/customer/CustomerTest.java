package woowacourse.shoppingcart.domain.customer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import woowacourse.shoppingcart.exception.PasswordMisMatchException;

class CustomerTest {

    private final String username = "dongho108";
    private final String password = "password1234";
    private final String phoneNumber = "01012341234";
    private final String address = "인천 서구 검단로";

    @DisplayName("Customer를 생성해야합니다.")
    @Test
    void createCustomer() {
        final Customer customer = Customer.of(username, password, phoneNumber, address);

        assertAll(
            () -> assertThat(customer.getUsername()).isEqualTo(new Username(username)),
            () -> assertThat(customer.getPassword()).isEqualTo(new Password(password)),
            () -> assertThat(customer.getPhoneNumber()).isEqualTo(new PhoneNumber(phoneNumber)),
            () -> assertThat(customer.getAddress()).isEqualTo(address)
        );
    }

    @DisplayName("비밀번호가 일치하면 예외를 반환하지 않아야 한다.")
    @Test
    void matchPassword() {
        final Customer customer = Customer.of(username, password, phoneNumber, address);

        assertDoesNotThrow(() -> customer.matchPassword("password1234"));
    }

    @DisplayName("비밀번호가 일치하지 않으면 예외를 반환해야 한다.")
    @Test
    void matchWrongPassword() {
        final Customer customer = Customer.of(username, password, phoneNumber, address);

        assertThatThrownBy(() -> customer.matchPassword("1234pass"))
            .hasMessage("비밀번호가 일치하지 않습니다.")
            .isInstanceOf(PasswordMisMatchException.class);
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
