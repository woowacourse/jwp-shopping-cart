package woowacourse.shoppingcart.dto;

import java.util.List;

public class CartItemsResponse {

    private List<CartItemResponse> carts;

    public CartItemsResponse() {
    }

    public CartItemsResponse(List<CartItemResponse> carts) {
        this.carts = carts;
    }

    public List<CartItemResponse> getCarts() {
        return carts;
    }
}
