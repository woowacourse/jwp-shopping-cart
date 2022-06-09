package woowacourse.shoppingcart.domain.customer;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import woowacourse.shoppingcart.exception.InputFormatException;

public class EmailTest {

    @ParameterizedTest
    @DisplayName("이메일이 규약에 맞으면 정상적으로 생성된다.")
    @ValueSource(strings = {"test@email.com", "testtesttest@gmail.com"})
    void InputRightEmail(String value) {
        //then
        assertThatCode(() -> Email.of(value))
                .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @DisplayName("패턴에 맞지 않는 이메일이라면 에러를 발생한다.")
    @EmptySource
    @ValueSource(strings = {"test.gmail.com", "test@com", "test"})
    void InputWrongPatternEmail(String value) {
        //then
        assertThatThrownBy(() -> Email.of(value))
                .isInstanceOf(InputFormatException.class);
    }
}
