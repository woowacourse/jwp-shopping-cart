package woowacourse.shoppingcart.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class UserNameTest {
    @ParameterizedTest
    @CsvSource(value = {"칙 촉,공백", "012345678901234567890123456789012, 32"})
    void 공백을_포함하거나_길이가_32자를_초과하는_경우(String invalidName, String errorMessage) {
        assertThatThrownBy(() -> new UserName(invalidName))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(errorMessage);
    }
}
