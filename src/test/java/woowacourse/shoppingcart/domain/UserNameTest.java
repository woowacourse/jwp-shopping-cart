package woowacourse.shoppingcart.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import woowacourse.shoppingcart.domain.customer.Name;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

public class UserNameTest {

    @ParameterizedTest
    @CsvSource(value = {"칙 촉,공백", "012345678901234567890123456789012, 32"})
    void 공백을_포함하거나_길이가_32자를_초과하는_경우(final String invalidName, final String errorMessage) {
        assertThatThrownBy(() -> new Name(invalidName))
                .isInstanceOf(InvalidCustomerException.class)
                .hasMessageContaining(errorMessage);
    }
}
