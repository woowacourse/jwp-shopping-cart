package cart.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.*;

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

    @Test
    @DisplayName("null로 초기화할 수 없다")
    void init_null_throw() {
        assertThatThrownBy(() -> new Price(null)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("소수점아래숫자가 모두 0이면 소수점표현이 없는 것과 같다")
    void equalsTest() {
        Price price1 = new Price(new BigDecimal("5000"));
        Price price2 = new Price(new BigDecimal("5000.00"));

        assertThat(price1).isEqualTo(price2);
    }
}
