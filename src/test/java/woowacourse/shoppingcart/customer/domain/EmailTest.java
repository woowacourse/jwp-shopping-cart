package woowacourse.shoppingcart.customer.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import woowacourse.shoppingcart.customer.support.exception.CustomerException;
import woowacourse.shoppingcart.customer.support.exception.CustomerExceptionCode;

class EmailTest {

    @DisplayName("이메일은 형식에 따라야 한다.")
    @ParameterizedTest
    @ValueSource(strings = {"", "@", "a@b", "a@b.", "a@b.c"})
    void validateEmail(final String email) {
        assertThatThrownBy(() -> new Email(email))
                .isInstanceOf(CustomerException.class)
                .extracting("exceptionCode")
                .isEqualTo(CustomerExceptionCode.INVALID_FORMAT_EMAIL);
    }
}
