package woowacourse.shoppingcart.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import woowacourse.shoppingcart.domain.customer.Email;
import woowacourse.shoppingcart.exception.bodyexception.ValidateException;

public class EmailTest {

    @DisplayName("이메일 형식이 잘못 된 경우 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"email", "email@", "@email.com"})
    void new_wrongForm_exceptionThrown(String value) {
        // when, then
        assertThatThrownBy(() -> new Email(value))
                .isInstanceOf(ValidateException.class)
                .hasMessage("이메일 양식이 잘못 되었습니다.");
    }
}
