package woowacourse.shoppingcart.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import woowacourse.shoppingcart.domain.customer.Email;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

public class UserEmailTest {

    @Test
    void 이메일에_길이가_64자를_초과하는_경우() {
        final var invalidEmail = "0123456789012345678901234567890123456789012345678901234@naver.com";

        assertThatThrownBy(() -> new Email(invalidEmail))
                .isInstanceOf(InvalidCustomerException.class)
                .hasMessageContaining("64자");
    }
}
