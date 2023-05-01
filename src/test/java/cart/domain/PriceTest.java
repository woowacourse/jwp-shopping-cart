package cart.domain;

import cart.domain.product.Price;
import cart.exception.PriceCreateFailException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class PriceTest {

    @Test
    @DisplayName("가격을 정상적으로 생성한다.")
    void create_price_success() {
        // given
        int price = 1000;

        // when & then
        assertDoesNotThrow(() -> new Price(price));
    }

    @Test
    @DisplayName("가격이 0원 미만인 경우 예외를 발생한다.")
    void throws_exception_when_price_is_less_than_minimum() {
        // given
        int price = -100;

        // when & then
        assertThatThrownBy(() -> new Price(price))
                .isInstanceOf(PriceCreateFailException.class);
    }

    @Test
    @DisplayName("가격을 수정한다.")
    void edit_success() {
        // given
        Price price = new Price(1000);
        int expected = 500;

        // when
        price.edit(expected);

        // then
        assertThat(price.getPrice()).isEqualTo(expected);
    }

    @Test
    @DisplayName("가격 수정에 실패한다.")
    void edit_fail_when_invalid_price() {
        // given
        Price price = new Price(1000);
        int expected = -100;

        // when & then
        assertThatThrownBy(() -> price.edit(expected))
                .isInstanceOf(PriceCreateFailException.class);
    }
}
