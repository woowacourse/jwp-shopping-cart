package cart.domain;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class PriceTest {

    @ParameterizedTest(name = "{0}은 price로 유효하다")
    @ValueSource(strings = {"1000", "1000000"})
    void init_inRange_notThrow(String value) {
        assertThatCode(() -> new Price(new BigDecimal(value))).doesNotThrowAnyException();
    }

    @ParameterizedTest(name = "{0}은 price로 유효하지 않다.")
    @ValueSource(strings = {"100", "500", "999", "1000001"})
    void init_outRange_throw(String value) {
        assertThatThrownBy(() -> new Price(new BigDecimal(value))).isInstanceOf(IllegalArgumentException.class);
    }

}
