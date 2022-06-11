package woowacourse.shoppingcart.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import woowacourse.shoppingcart.exception.IllegalCartItemException;
import woowacourse.shoppingcart.exception.InvalidCartItemException;

class CartTest {

    @DisplayName("장바구니에 이미 담긴 상품을 추가하면 예외가 발생한다.")
    @Test
    void addItem_already_added() {
        Product product1 = new Product(1L, "당근", 1000, "image.com");
        CartItem cartItem1 = new CartItem(product1, 1);
        Cart cart = new Cart(List.of(cartItem1));

        Product product2 = new Product(1L, "당근", 1000, "image.com");
        CartItem cartItem2 = new CartItem(product2, 3);

        assertThatExceptionOfType(IllegalCartItemException.class)
                .isThrownBy(() -> cart.addItem(cartItem2))
                .withMessageContaining("이미 담긴");
    }

    @DisplayName("장바구니에 이미 담긴 상품의 수량을 수정할 수 있다.")
    @Test
    void changeQuantity() {
        Product product = new Product(1L, "당근", 1000, "image.com");
        CartItem cartItem = new CartItem(product, 1);
        Cart cart = new Cart(List.of(cartItem));

        cart.changeQuantity(1L, 3);

        CartItem actual = cart.getItemBy(1L);
        assertThat(actual.getQuantity()).isEqualTo(3);
    }

    @DisplayName("장바구니에 담기지 않은 상품의 수량을 수정하면 예외가 발생한다.")
    @Test
    void changeQuantity_not_added() {
        Product product = new Product(1L, "당근", 1000, "image.com");
        CartItem cartItem = new CartItem(product, 1);
        Cart cart = new Cart(List.of(cartItem));

        assertThatExceptionOfType(InvalidCartItemException.class)
                .isThrownBy(() -> cart.changeQuantity(2L, 3))
                .withMessageContaining("추가되지 않은");
    }

    @DisplayName("원하는 상품을 골라 주문할 수 있다.")
    @Test
    void checkOut() {
        Product carrot = new Product(1L, "당근", 1000, "image/carrot");
        Product apple = new Product(2L, "사과", 1200, "image/apple");
        Product potato = new Product(3L, "감자", 1300, "image/potato");

        CartItem carrotInCart = new CartItem(carrot, 1);
        CartItem appleInCart = new CartItem(apple, 1);
        CartItem potatoInCart = new CartItem(potato, 1);

        Cart cart = new Cart(List.of(carrotInCart, appleInCart, potatoInCart));
        Orders orders = cart.checkOut(List.of(1L, 2L));

        List<Long> orderedIds = orders.getOrderDetails()
                .stream()
                .map(OrderDetail::getProductId)
                .collect(Collectors.toList());

        List<Long> remainingIds = cart.getItems()
                .stream()
                .map(CartItem::getProductId)
                .collect(Collectors.toList());

        assertThat(orderedIds).containsExactlyInAnyOrder(1L, 2L);
        assertThat(remainingIds).containsExactly(3L);
    }
}
