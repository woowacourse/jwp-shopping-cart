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
        Product product = new Product("banana", 1_000, 1, "woowa1.com");
        assertThatThrownBy(() -> new CartItem(1L, product, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("상품 수량은 0보다 커야 합니다.");
    }

    @DisplayName("상품을 여러 개 추가한다.")
    @Test
    void add() {
        Product product = new Product("banana", 1_000, 10, "woowa1.com");
        CartItem cartItem = new CartItem(1L, product, 1);

        cartItem.addQuantity(5);

        assertThat(cartItem.getQuantity()).isEqualTo(6);
    }

    @DisplayName("상품의 개수를 변경한다.")
    @Test
    void replaceQuantity() {
        Product product = new Product("banana", 1_000, 10, "woowa1.com");
        CartItem cartItem = new CartItem(1L, product, 1);

        cartItem.replaceQuantity(5);

        assertThat(cartItem.getQuantity()).isEqualTo(5);
    }

    @DisplayName("장바구니에 담긴 상품 수량이 없는지 반환한다.")
    @ParameterizedTest
    @CsvSource({"0,true", "1,false"})
    void isEmpty(int quantity, boolean expected) {
        Product product = new Product("banana", 1_000, 10, "woowa1.com");
        CartItem cartItem = new CartItem(1L, product, 1);

        cartItem.replaceQuantity(quantity);

        assertThat(cartItem.isEmpty()).isEqualTo(expected);
    }
}
