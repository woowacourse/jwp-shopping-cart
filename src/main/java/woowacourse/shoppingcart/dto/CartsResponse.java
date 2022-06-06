package woowacourse.shoppingcart.dto;

import java.util.List;

public class CartsResponse {
    private List<CartResponse> cartItems;

    private CartsResponse() {
    }

    public CartsResponse(List<CartResponse> cartResponses) {
        this.cartItems = cartResponses;
    }

    public List<CartResponse> getCartItems() {
        return cartItems;
    }
}
