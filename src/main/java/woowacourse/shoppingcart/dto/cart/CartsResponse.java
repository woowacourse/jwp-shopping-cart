package woowacourse.shoppingcart.dto.cart;

import java.util.List;
import java.util.stream.Collectors;
import woowacourse.shoppingcart.domain.Cart;

public class CartsResponse {
    private final List<CartResponse> carts;

    public CartsResponse(List<Cart> carts) {
        this.carts = carts.stream()
                .map(CartResponse::of)
                .collect(Collectors.toList());
    }

    public List<CartResponse> getCarts() {
        return carts;
    }
}
