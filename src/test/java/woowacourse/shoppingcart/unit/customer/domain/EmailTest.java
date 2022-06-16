package woowacourse.shoppingcart.unit.customer.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import woowacourse.shoppingcart.customer.domain.Email;
import woowacourse.shoppingcart.customer.exception.badrequest.InvalidEmailException;

class EmailTest {

    @ParameterizedTest
    @DisplayName("이메일이 유효하지 않으면 예외를 던진다.")
    @ValueSource(strings = {"rick#gmail.com", "rick@gmail", "rick.gmail.com", "rick@gmail.c", "@gmail.com",
            "rick@gmail.orggg"})
    void newEmail_invalidValue_exceptionThrown(final String value) {
        // when, then
        Assertions.assertThatThrownBy(() -> new Email(value))
                .isInstanceOf(InvalidEmailException.class);
    }
}