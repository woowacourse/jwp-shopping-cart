package cart.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PriceTest {

    @ParameterizedTest
    @ValueSource(ints = {0, -1})
    @DisplayName("validateIsPositive() : 가격을 0 또는 음수로 입력 시 IllegalArgumentException가 발생합니다.")
    void test_validateIsPositive_IllegalArgumentException(final int value) throws Exception {
        //when & then
        assertThatThrownBy(() -> new Price(value))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
