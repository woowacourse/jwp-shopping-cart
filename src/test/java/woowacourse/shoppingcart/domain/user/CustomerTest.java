package woowacourse.shoppingcart.domain.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import woowacourse.shoppingcart.domain.user.Customer;
import woowacourse.shoppingcart.domain.user.Password;

public class CustomerTest {

    @DisplayName("비밀번호가 일치 여부 확인")
    @ParameterizedTest
    @CsvSource({"12345678a,true", "12345678b,false"})
    void isValidPassword(String password, boolean expected) {
        Customer customer = new Customer("email@email.com", "12345678a", "tonic");

        assertThat(customer.isValidPassword(new Password(password))).isEqualTo(expected);
    }
}
