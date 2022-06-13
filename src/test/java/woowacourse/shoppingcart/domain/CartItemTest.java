package woowacourse.shoppingcart.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CartItemTest {

    @Test
    @DisplayName("장바구니 객체의 수량을 적용한다.")
    void applyQuantity() {
        // given
        final int initValue = 1;
        final CartItem rice = new CartItem(1L, 1L, "밥", 1000, "www.naver.com", initValue);
        final int updateValue = 3;

        // when
        final CartItem updatedRice = rice.applyQuantity(updateValue);

        // then
        assertThat(updatedRice.getQuantity()).isEqualTo(updateValue);
    }

}
