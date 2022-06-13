package woowacourse.shoppingcart.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static woowacourse.Fixtures.치킨;
import static woowacourse.Fixtures.피자;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CartsTest {

    @Test
    @DisplayName("해당 카트의 id 들을 반환한다.")
    void getCartIds() {
        //given
        Cart 치킨카트 = new Cart(1L, 1, 치킨);
        Cart 피자카트 = new Cart(2L, 1, 피자);
        Carts carts = new Carts(List.of(치킨카트, 피자카트));

        //when
        List<Long> cartIds = carts.getCartIds();

        //then
        assertThat(cartIds)
                .containsExactly(1L, 2L);
    }

    @Test
    @DisplayName("해당 카트에 없는 물품의 id 들을 반환한다.")
    void findNotInProductIds() {
        //given
        Cart 치킨카트 = new Cart(1L, 1, 치킨);
        Cart 피자카트 = new Cart(2L, 1, 피자);
        Carts carts = new Carts(List.of(치킨카트, 피자카트));

        //when
        List<Long> notInProductIds = carts.findNotInProductIds(List.of(1L, 2L, 100L));

        //then
        assertThat(notInProductIds)
                .containsOnly(100L);
    }
}
