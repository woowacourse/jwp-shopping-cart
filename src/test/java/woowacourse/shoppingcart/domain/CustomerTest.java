package woowacourse.shoppingcart.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CustomerTest {

    private Customer customer;

    @BeforeEach
    void setUp() {
        customer = new Customer(1L, new Email("email@email.com"), "카일",
                Password.encodePassword("$2a$10$vZAeMlN93.GghKBYfuOVSOtfbPsRh9LM/PloKlExao754bDBm6F/S"));
    }

    @DisplayName("요청된 이름에 따라 고객의 이름이 바껴야 한다.")
    @Test
    void changeName() {
        // given
        final String newName = "카이";

        // when
        final Customer newCustomer = customer.changeName(newName);

        // then
        assertThat(newCustomer.getName()).isEqualTo(newName);
    }

    @DisplayName("요청된 비밀번호에 따라 고객의 비밀번호가 바껴야 한다.")
    @Test
    void changePassword() {
        // given
        final Password newPassword = Password.planePassword("*A123456789");

        // when
        final Customer newCustomer = customer.changePassword(newPassword);

        // then
        assertThat(newCustomer.getPassword()).isEqualTo(newPassword.getPassword());
    }

    @DisplayName("요청된 비밀번호가 기존의 비밀번호와 일치하지 않으면 false를 반환한다.")
    @Test
    void validatePasswordWithIncorrectPassword() {
        // given
        String wrongPassword = "12345678" + "wrong";

        //when //then
        assertThat(customer.isCorrectPassword(wrongPassword)).isFalse();
    }

    @DisplayName("요청된 비밀번호가 기존의 비밀번호와 일치하면 true를 반환한다.")
    @Test
    void validatePassword() {
        // given
        String wrongPassword = "12345678";

        //when //then
        assertThat(customer.isCorrectPassword(wrongPassword)).isTrue();
    }
}
