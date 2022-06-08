package woowacourse.shoppingcart.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import woowacourse.shoppingcart.domain.product.Price;

class PriceTest {

    @Test
    @DisplayName("가격 객체를 생성한다.")
    void createPrice() {
        //given

        //when
        Price price = new Price(1000);
        //then
        assertThat(price.getValue()).isEqualTo(1000);
    }

    @Test
    @DisplayName("음수 가격은 예외를 반환한다.")
    void notAllowedNegativePrice() {
        //given

        //when

        //then
        assertThatThrownBy(() -> new Price(-1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("가격은 음수가 될 수 없습니다.");
    }

}
