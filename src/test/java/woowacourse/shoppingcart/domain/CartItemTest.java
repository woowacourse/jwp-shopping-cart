package woowacourse.shoppingcart.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class CartItemTest {

    @DisplayName("장바구니에 담는 상품의 수량이 0 이하면 예외를 반환한다.")
    @Test
    void quantityCannotLessThanOrEqualTo0() {
        assertThatThrownBy(() -> new CartItem(1L, 1L, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("상품 수량은 0보다 커야 합니다.");
    }

    @DisplayName("상품을 하나 더 추가한다.")
    @Test
    void addOne() {
        CartItem cartItem = new CartItem(1L, 1L, 1);

        cartItem.addOne();

        assertThat(cartItem.getQuantity()).isEqualTo(2);
    }

    @DisplayName("상품을 여러 개 추가한다.")
    @Test
    void add() {
        CartItem cartItem = new CartItem(1L, 1L, 1);

        cartItem.add(5);

        assertThat(cartItem.getQuantity()).isEqualTo(6);
    }

    @DisplayName("상품을 한 개 뺀다.")
    @Test
    void reduceOne() {
        CartItem cartItem = new CartItem(1L, 1L, 1);

        cartItem.reduceOne();

        assertThat(cartItem.getQuantity()).isEqualTo(0);
    }

    @DisplayName("수량이 없는 상품은 뺄 수 없다.")
    @Test
    void cannotReduceEmptyCartItem() {
        CartItem cartItem = new CartItem(1L, 1L, 1);

        cartItem.reduceOne();

        assertThatThrownBy(cartItem::reduceOne)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("더 이상 수량을 줄일 수 없습니다.");
    }

    @DisplayName("장바구니에 담긴 상품 수량이 없는지 반환한다.")
    @ParameterizedTest
    @CsvSource({"1,true", "2,false"})
    void isEmpty(int quantity, boolean expected) {
        CartItem cartItem = new CartItem(1L, 1L, quantity);

        cartItem.reduceOne();

        assertThat(cartItem.isEmpty()).isEqualTo(expected);
    }
}
