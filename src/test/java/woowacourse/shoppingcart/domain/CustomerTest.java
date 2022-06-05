package woowacourse.shoppingcart.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import woowacourse.shoppingcart.util.BcryptConvertor;

class CustomerTest {

    private static final String NAME = "클레이";
    private static final String RAW_EMAIL = "clay@gmail.com";
    private static final Email EMAIL = new Email(RAW_EMAIL);
    private static final String RAW_PASSWORD = "12345678";
    private static final PasswordConvertor PASSWORD_CONVERTOR = new BcryptConvertor();
    private static final Password PASSWORD = Password.fromRawValue(RAW_PASSWORD, PASSWORD_CONVERTOR);

    @DisplayName("비밀번호가 일치하는지 확인한다.")
    @ParameterizedTest
    @CsvSource(value = {"12345678:false", "1312321312323:true"}, delimiter = ':')
    void isSamePassword(final String password, final boolean expected) {
        // given
        final Customer customer = new Customer(NAME, EMAIL, PASSWORD);

        // when
        final boolean actual = customer.unMatchPasswordWith(password, PASSWORD_CONVERTOR);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("이름을 변경한다.")
    void updateName() {
        // given
        final Customer customer = new Customer(NAME, EMAIL, PASSWORD);
        final String newName = "썬";

        // when
        final Customer actual = customer.updateName(newName);

        // then
        assertThat(actual.getName()).isEqualTo(newName);
    }

    @Test
    @DisplayName("비밀번호를 변경한다.")
    void updatePassword() {
        // given
        final Customer customer = new Customer(NAME, EMAIL, PASSWORD);
        final Password newPassword = Password.fromRawValue("1234567890", PASSWORD_CONVERTOR);

        // when
        final Customer actual = customer.updatePassword(newPassword);

        // then
        assertThat(actual.getPassword()).isEqualTo(newPassword);
    }
}
