package woowacourse.shoppingcart.domain.customer;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import woowacourse.auth.domain.BcryptPasswordMatcher;

class CustomerTest {

    @Test
    @DisplayName("회원을 생성한다.")
    void createCustomer() {
        assertThatCode(() -> Customer.fromInput("chleeslow", "1234abc!@", "woote@email.com", "선릉역", "010-9999-1111"))
            .doesNotThrowAnyException();
    }

    @DisplayName("회원 정보를 수정한다.")
    @Test
    void updateCustomer() {
        // given
        final Customer customer = Customer.fromInput("chleeslow", "1234abc!@", "woote@email.com", "선릉역",
            "010-9999-1111");
        // when
        final Customer updatedCustomer = customer.update("new-address", "010-1212-3434");
        // then
        Assertions.assertAll(
            () -> assertThat(updatedCustomer.getId()).isEqualTo(customer.getId()),
            () -> assertThat(updatedCustomer.getAddress()).isEqualTo("new-address"),
            () -> assertThat(updatedCustomer.getPhoneNumber()).isEqualTo("010-1212-3434")
        );
    }

    @DisplayName("패스워드를 암호화한다.")
    @Test
    void encryptCustomerPassword() {
        // given
        final Customer customer = Customer.fromInput("chleeslow", "1234abc!@", "woote@email.com", "선릉역",
            "010-9999-1111");
        // when
        final Customer encrypted = customer.encryptPassword(new BcryptPasswordEncryptor());
        // then
        assertThat(encrypted.getPassword()).isNotEqualTo(customer.getPassword());
    }

    @DisplayName("패스워드가 일치하는지 확인한다.")
    @Test
    void matchCustomerPassword() {
        // given
        final Customer customer =
            Customer.fromInput("chleeslow", "1234abc!@", "woote@email.com", "선릉역", "010-9999-1111")
                .encryptPassword(new BcryptPasswordEncryptor());

        // when
        final boolean isMatch = customer.isPasswordMatch("1234abc!@", new BcryptPasswordMatcher());

        // then
        assertThat(isMatch).isTrue();
    }
}
