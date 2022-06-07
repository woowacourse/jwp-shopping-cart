package woowacourse.shoppingcart.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import woowacourse.shoppingcart.domain.customer.RawPassword;
import woowacourse.shoppingcart.exception.InvalidPasswordLengthException;

public class RawPasswordTest {

    @ParameterizedTest
    @DisplayName("8자 이상 15자 이하가 아닌 문자열로 생성하려면 예외가 발생한다..")
    @ValueSource(strings = {"", "썬", "클레이", "1234567", "1234567890123456"})
    void createRawPassword_invalidLength_throwsException(String rawValue) {
        // given
        assertThatThrownBy(() -> new RawPassword(rawValue))
                .isInstanceOf(InvalidPasswordLengthException.class);
    }
}
