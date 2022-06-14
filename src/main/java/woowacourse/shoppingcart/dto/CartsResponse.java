package woowacourse.shoppingcart.dto;

import java.util.ArrayList;
import java.util.List;

public class CartsResponse {

    private final List<CartResponse> cartList;

    private CartsResponse() {
        this.cartList = new ArrayList<>();
    }

    public CartsResponse(List<CartResponse> cartList) {
        this.cartList = cartList;
    }

    public List<CartResponse> getCartList() {
        return cartList;
    }
}
