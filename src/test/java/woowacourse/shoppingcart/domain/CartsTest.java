package woowacourse.shoppingcart.domain;

import org.junit.jupiter.api.Test;
import woowacourse.shoppingcart.domain.cart.Cart;
import woowacourse.shoppingcart.domain.cart.CartId;
import woowacourse.shoppingcart.domain.cart.Quantity;
import woowacourse.shoppingcart.domain.product.*;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CartsTest {

    @Test
    void findQuantity() {
        final Product product = new Product(new ProductId(1), new Name("coffee"), new Price(10000), new Thumbnail("coffee.png"));
        Cart cart = new Cart(new CartId(1), product, new Quantity(5));
        Carts carts = new Carts(List.of(cart));
        Quantity quantityByProduct = carts.findQuantity(product);
        final int result = quantityByProduct.getValue();

        assertThat(result).isEqualTo(5);

    }
}
