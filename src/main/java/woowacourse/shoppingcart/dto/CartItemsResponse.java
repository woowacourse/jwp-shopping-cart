package woowacourse.shoppingcart.dto;

import java.util.List;

public class CartItemsResponse {

    private final List<CartItemResponse> carts;

    public CartItemsResponse(List<CartItemResponse> carts) {
        this.carts = carts;
    }

    public List<CartItemResponse> getCarts() {
        return carts;
    }
}
