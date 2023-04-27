package cart.domain;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class PriceTest {

    @ParameterizedTest(name = "{0}은 price로 유효하다")
    @ValueSource(ints = {1000, 1_000_000})
    void init_inRange_notThrow(int value) {
        assertThatCode(() -> new Price(value)).doesNotThrowAnyException();
    }

    @ParameterizedTest(name = "{0}은 price로 유효하지 않다.")
    @ValueSource(ints = {100, 500, 999, 1_000_001})
    void init_outRange_throw(int value) {
        assertThatThrownBy(() -> new Price(value)).isInstanceOf(IllegalArgumentException.class);
    }

}
