package woowacourse.shoppingcart.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static woowacourse.fixture.TestConstant.PARAM_TEST_NAME;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import woowacourse.shoppingcart.domain.customer.Username;

public class UsernameTest {

    @DisplayName("username은 1자 이상 10자 이하가 아니면 예외를 발생시킨다.")
    @ParameterizedTest(name = PARAM_TEST_NAME)
    @ValueSource(strings = {"", "12345678901"})
    void validateLength(String username) {
        assertThatThrownBy(() -> new Username(username))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("username은 공백이 들어가면 예외를 발생시킨다.")
    @ParameterizedTest(name = PARAM_TEST_NAME)
    @ValueSource(strings = {"h ell", "    "})
    void validateBlank(String username) {
        assertThatThrownBy(() -> new Username(username))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
