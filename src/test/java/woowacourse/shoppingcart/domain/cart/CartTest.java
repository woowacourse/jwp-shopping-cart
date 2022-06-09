package woowacourse.shoppingcart.domain.cart;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import woowacourse.shoppingcart.domain.product.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CartTest {

    @DisplayName("같은 상품을 가지고 있는 장바구니인 것을 확인한다.")
    @Test
    void isSame_True() {
        Cart cart = new Cart(new CartId(1), new Product(new ProductId(1), new Name("coffee"), new Price(10000), new Thumbnail("coffee.png")), new Quantity(5));
        final Boolean result = cart.isSame(new Product(new ProductId(1), new Name("coffee"), new Price(10000), new Thumbnail("coffee.png")));

        assertThat(result).isTrue();
    }

    @DisplayName("같은 상품을 가지고 있는 장바구니가 아닌 것을 확인한다.")
    @Test
    void isSame_False() {
        Cart cart = new Cart(new CartId(1), new Product(new ProductId(1), new Name("coffee"), new Price(10000), new Thumbnail("coffee.png")), new Quantity(5));
        final Boolean result = cart.isSame(new Product(new ProductId(2), new Name("coffee"), new Price(10000), new Thumbnail("coffee.png")));

        assertThat(result).isFalse();
    }
}
