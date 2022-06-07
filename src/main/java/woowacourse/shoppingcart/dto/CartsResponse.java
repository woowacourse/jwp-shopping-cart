package woowacourse.shoppingcart.dto;

import java.util.List;

public class CartsResponse {

    private final List<CartResponse> products;

    public CartsResponse(List<CartResponse> products) {
        this.products = products;
    }

    public List<CartResponse> getProducts() {
        return products;
    }
}
