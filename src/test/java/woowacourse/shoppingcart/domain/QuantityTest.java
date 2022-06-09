package woowacourse.shoppingcart.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import woowacourse.shoppingcart.exception.IllegalFormException;

class QuantityTest {

    @DisplayName("수량의 범위를 검증한다.")
    @ParameterizedTest
    @ValueSource(ints = {-1, 100})
    void validateRange(int value) {
        assertThatThrownBy(() -> new Quantity(value))
                .isExactlyInstanceOf(IllegalFormException.class)
                .hasMessageContaining("수량의 입력 형식에 맞지 않습니다.");
    }
}
