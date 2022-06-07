package woowacourse.shoppingcart.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.domain.customer.HashedPassword;
import woowacourse.shoppingcart.domain.customer.RawPassword;

class CustomerTest {

    private static final String NAME = "클레이";
    private static final String EMAIL = "clay@gmail.com";
    private static final String RAW_PASSWORD = "12345678";
    private static final HashedPassword PASSWORD = HashedPassword.from(new RawPassword(RAW_PASSWORD));

    @DisplayName("비밀번호가 일치하는지 확인한다.")
    @ParameterizedTest
    @CsvSource(value = {"12345678:false", "1312321312323:true"}, delimiter = ':')
    void isSamePassword(final String password, final boolean expected) {
        // given
        final Customer customer = new Customer(NAME, EMAIL, PASSWORD);

        // when
        final boolean actual = customer.unMatchPasswordWith(password);

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
        final HashedPassword newPassword = HashedPassword.from(new RawPassword("1234567890"));

        // when
        final Customer actual = customer.updatePassword(newPassword);

        // then
        assertThat(actual.getPassword()).isEqualTo(newPassword);
    }
}
