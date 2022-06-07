package woowacourse.shoppingcart.customer.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class CustomerTest {

    private static final String CUSTOMER_NAME = "guest";
    private static final String CUSTOMER_EMAIL = "guest@woowa.com";

    @DisplayName("비밀번호를 비교한다.")
    @ParameterizedTest
    @CsvSource(value = {"1234567890a!,1234567890a!,true", "1234567890a!,1234567890f^,false"})
    void equalsPassword(final String source, final String target, final boolean expected) {
        final Customer customer = new Customer(CUSTOMER_EMAIL, CUSTOMER_NAME, source);

        assertThat(customer.equalsPassword(target)).isEqualTo(expected);
    }
}
