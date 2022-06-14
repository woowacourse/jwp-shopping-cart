package woowacourse.shoppingcart.domain.product;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import woowacourse.shoppingcart.exception.InvalidFormException;

class PriceTest {

    @Test
    @DisplayName("가격이 음수인 경우, 예외를 발생한다.")
    void invalidPriceException() {
        assertThatExceptionOfType(InvalidFormException.class)
                .isThrownBy(() -> new Price(-1));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1})
    @DisplayName("가격이 양수인 경우, 정상적으로 생성한다.")
    void createPrice(int value) {
        Price price = new Price(value);
        assertThat(price.getValue()).isEqualTo(value);
    }
}
