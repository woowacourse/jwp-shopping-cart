package woowacourse.shoppingcart.domain.customer;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import woowacourse.common.exception.InputFormatException;

class UsernameTest {

    @ParameterizedTest(name = "{0}은 1~10자가 아니므로 에러를 발생시킨다.")
    @ValueSource(strings = {"", "12345123451"})
    void username(String input) {
        assertThatThrownBy(() -> new Username(input)).isInstanceOf(InputFormatException.class);
    }
}