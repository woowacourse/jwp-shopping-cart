package woowacourse.shoppingcart.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class PasswordTest {
    @ParameterizedTest
    @CsvSource(value = {"ㄱ01234,한글", "0 12345,공백", "01234,길이"})
    void 한글_및_공백_또는_6자_미만인_경우(String invalidPassword, String message) {
        assertThatThrownBy(() -> new Password(invalidPassword))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(message);
    }
}
