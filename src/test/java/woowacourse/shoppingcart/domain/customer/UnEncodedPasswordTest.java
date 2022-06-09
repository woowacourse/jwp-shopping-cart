package woowacourse.shoppingcart.domain.customer;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static woowacourse.fixture.TestConstant.PARAM_TEST_NAME;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class UnEncodedPasswordTest {

    @DisplayName("비밀번호는 8자 이상 20자 이하가 아니면 예외가 발생한다.")
    @ParameterizedTest(name = PARAM_TEST_NAME)
    @ValueSource(strings = {"1234567", "123456789012345678901"})
    void validateLength(String password) {
        assertThatThrownBy(() -> new UnEncodedPassword(password))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("비밀번호는 8자 이상 20자 이하여야합니다.");
    }

    @DisplayName("비밀번호에 공백이 포함되면 예외가 발생한다.")
    @ParameterizedTest(name = PARAM_TEST_NAME)
    @ValueSource(strings = {"        ", "never stop"})
    void validateEmpty(String password) {
        assertThatThrownBy(() -> new UnEncodedPassword(password))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("비밀번호에는 공백이 있으면 안됩니다.");
    }
}
