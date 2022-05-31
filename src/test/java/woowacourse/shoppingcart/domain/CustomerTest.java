package woowacourse.shoppingcart.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import woowacourse.shoppingcart.exception.InvalidInputException;

class CustomerTest {

    @DisplayName("올바르지 않은 포맷의 username 이다.")
    @ParameterizedTest(name = "올바르지 않은 username - {0}")
    @ValueSource(strings = {"abc", "abc@navercom", "abcnaver.com", "@naver.com", "abc.naver.com", "", " "})
    void invalidUserName(String input) {
        assertThatThrownBy(() -> new Customer(1L, input, "Abcd1234", "jojo", false))
                .isInstanceOf(InvalidInputException.class)
                .hasMessage("올바르지 않은 포맷의 아이디 입니다.");
    }
}
