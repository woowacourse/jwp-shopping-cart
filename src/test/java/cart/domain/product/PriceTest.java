package cart.domain.product;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class PriceTest {

    @ParameterizedTest
    @ValueSource(ints = {0, -1, Integer.MIN_VALUE})
    @DisplayName("가격이 0원 이하이면 예외가 발생해야 한다.")
    void create_priceIsZeroOrLess(int price) {
        // given
        assertThatThrownBy(() -> new Price(price))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("가격은 0원 이하 일 수 없습니다.");
    }

    @Test
    @DisplayName("가격이 10,000,000원 이상이면 예외가 발생해야 한다.")
    void create_priceIsOverThan10_000_000() {
        // given
        assertThatThrownBy(() -> new Price(10_000_000))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("가격은 10000000원 이상 일 수 없습니다.");
    }

    @Test
    @DisplayName("가격이 정상적으로 생성되어야 한다.")
    void create_success() {
        // given
        Price price = new Price(10000);

        // expect
        assertThat(price.getPrice())
                .isEqualTo(10000);
    }
}
