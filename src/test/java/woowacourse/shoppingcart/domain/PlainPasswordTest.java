package woowacourse.shoppingcart.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import woowacourse.shoppingcart.domain.exception.InvalidPasswordLengthException;

class PlainPasswordTest {

    @ParameterizedTest
    @DisplayName("평문이 8자리 미만일 경우 예외가 발생한다.")
    @ValueSource(strings = {"", "썬", "클레이", "1234567"})
    void create_lengthLessThanEight_throwsException(final String rawValue) {
        // when, then
        assertThatThrownBy(() -> new PlainPassword(rawValue))
                .isInstanceOf(InvalidPasswordLengthException.class);
    }
}
