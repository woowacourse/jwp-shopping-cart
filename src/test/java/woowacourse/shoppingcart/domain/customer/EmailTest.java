package woowacourse.shoppingcart.domain.customer;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import woowacourse.exception.InvalidEmailFormatException;

class EmailTest {

    @DisplayName("이메일은 이메일 형식에 어긋나선 안된다.")
    @ParameterizedTest
    @ValueSource(strings = {"", "@", "a@b", "a@b.", "a@b.c"})
    void validateEmail(final String email) {
        assertThatThrownBy(() -> new Email(email))
                .isInstanceOf(InvalidEmailFormatException.class);
    }
}
