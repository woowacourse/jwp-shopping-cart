package woowacourse.shoppingcart.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import woowacourse.shoppingcart.exception.custum.InvalidInputException;

public class PasswordTest {

    @DisplayName("올바르지 않은 포맷의 password 이다.")
    @ParameterizedTest(name = "올바르지 않은 password - {0}")
    @ValueSource(strings = {"1q@4567", "12345678", "abcdefgh", "!@#$%^&*", "1234567a", "abcd!@#$", "1234%^&*",
            "12345678abcdefgh!"})
    void invalidUserPassword(String input) {
        assertThatThrownBy(() -> new Password(input))
                .isInstanceOf(InvalidInputException.class)
                .hasMessage("올바르지 않은 포맷의 패스워드 입니다.");
    }
}
