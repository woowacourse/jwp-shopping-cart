package woowacourse.shoppingcart.dto.cart;

import java.util.List;

public class CartsResponse {

    private final List<CartResponse> carts;

    public CartsResponse(final List<CartResponse> carts) {
        this.carts = carts;
    }

    public List<CartResponse> getCarts() {
        return carts;
    }
}
