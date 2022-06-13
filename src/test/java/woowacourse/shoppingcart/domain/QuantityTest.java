package woowacourse.shoppingcart.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import woowacourse.shoppingcart.exception.QuantityRangeException;

class QuantityTest {

    @DisplayName("수량의 범위를 검증한다.")
    @ParameterizedTest
    @ValueSource(ints = {-1, 100})
    void validateRange(int value) {
        assertThatThrownBy(() -> new Quantity(value))
                .isExactlyInstanceOf(QuantityRangeException.class)
                .hasMessageContaining("수량은 0~99개까지 가능합니다.");
    }
}
