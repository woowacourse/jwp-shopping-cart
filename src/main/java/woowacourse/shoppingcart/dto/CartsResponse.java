package woowacourse.shoppingcart.dto;

import java.util.List;

public class CartsResponse {

    private final List<CartResponse> cartList;

    public CartsResponse(List<CartResponse> cartList) {
        this.cartList = cartList;
    }

    public List<CartResponse> getCartList() {
        return cartList;
    }
}
