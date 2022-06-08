package woowacourse.shoppingcart.dto;

import java.util.List;

public class CartsResponse {

    private List<CartResponse> carts;

    public CartsResponse() {
    }

    public CartsResponse(List<CartResponse> carts) {
        this.carts = carts;
    }

    public List<CartResponse> getCarts() {
        return carts;
    }
}
