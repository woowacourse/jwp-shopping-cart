package woowacourse.shoppingcart.domain.customer;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import woowacourse.shoppingcart.exception.PasswordMisMatchException;

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
            () -> assertThat(customer.getPassword()).isEqualTo(Password.from(password)),
            () -> assertThat(customer.getPhoneNumber()).isEqualTo(new PhoneNumber(phoneNumber)),
            () -> assertThat(customer.getAddress()).isEqualTo(address)
        );
    }

    @DisplayName("비밀번호가 일치하면 예외를 반환하지 않아야 한다.")
    @Test
    void matchPassword() {
        String username = "dongho108";
        String password = "password1234";
        String phoneNumber = "01012341234";
        String address = "인천 서구 검단로";

        Customer customer = Customer.of(username, password, phoneNumber, address);

        assertDoesNotThrow(() -> customer.matchPassword("password1234"));
    }

    @DisplayName("비밀번호가 일치하지 않으면 예외를 반환해야 한다.")
    @Test
    void matchWrongPassword() {
        String username = "dongho108";
        String password = "password1234";
        String phoneNumber = "01012341234";
        String address = "인천 서구 검단로";

        Customer customer = Customer.of(username, password, phoneNumber, address);

        assertThatThrownBy(() -> customer.matchPassword("1234pass"))
            .hasMessage("비밀번호가 일치하지 않습니다.")
            .isInstanceOf(PasswordMisMatchException.class);
    }

    @DisplayName("phoneNumber를 수정한다.")
    @Test
    void updatePhoneNumber() {
        String username = "dongho108";
        String password = "password1234";
        String phoneNumber = "01012341234";
        String address = "인천 서구 검단로";

        Customer customer = Customer.of(username, password, phoneNumber, address);
        String newPhoneNumber = "01011112222";
        customer.updatePhoneNumber(newPhoneNumber);

        assertThat(customer.getPhoneNumber().getValue()).isEqualTo(newPhoneNumber);
    }

    @DisplayName("address를 수정한다.")
    @Test
    void updateAddress() {
        String username = "dongho108";
        String password = "password1234";
        String phoneNumber = "01012341234";
        String address = "인천 서구 검단로";

        Customer customer = Customer.of(username, password, phoneNumber, address);
        String newAddress = "서울시 강남구";
        customer.updateAddress(newAddress);

        assertThat(customer.getAddress()).isEqualTo(newAddress);
    }

    @DisplayName("password를 수정한다.")
    @Test
    void updatePassword() {
        String username = "dongho108";
        String password = "password1234";
        String phoneNumber = "01012341234";
        String address = "인천 서구 검단로";

        Customer customer = Customer.of(username, password, phoneNumber, address);
        String newPassword = "password1111";
        customer.updatePassword(newPassword);

        assertThat(customer.getPassword()).isEqualTo(Password.from(newPassword));
    }
}
