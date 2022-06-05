package woowacourse.shoppingcart.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import woowacourse.shoppingcart.exception.IllegalPasswordException;

public class PasswordTest {

    @DisplayName("비밀번호 양식이 잘 못된 경우 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"asdfasdfaf", "123424352", "asdf123"})
    void new_wrongForm_exceptionThrown(String value) {
        // when, then
        assertThatThrownBy(() -> new Password(value))
                .isInstanceOf(IllegalPasswordException.class);
    }
}
