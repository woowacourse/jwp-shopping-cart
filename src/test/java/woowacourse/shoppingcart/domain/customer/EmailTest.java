package woowacourse.shoppingcart.domain.customer;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import woowacourse.common.exception.InputFormatException;

class EmailTest {

    @ParameterizedTest(name = "{0}은 이메일 형식에 맞지 않으므로 에러를 발생시킨다.")
    @ValueSource(strings = {"123", "123@com", "@google.com"})
    void email(String input) {
        assertThatThrownBy(() -> new Email(input)).isInstanceOf(InputFormatException.class);
    }
}