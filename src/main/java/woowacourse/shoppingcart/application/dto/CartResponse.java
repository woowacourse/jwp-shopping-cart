package woowacourse.shoppingcart.application.dto;

import java.util.List;

public class CartResponse {

    private final List<ProductResponse> cart;

    public CartResponse(List<ProductResponse> cart) {
        this.cart = cart;
    }

    public List<ProductResponse> getCart() {
        return cart;
    }
}
