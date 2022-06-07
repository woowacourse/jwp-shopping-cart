package woowacourse.shoppingcart.dto;

import java.util.List;

public class CartResponses {

    private List<CartResponse> products;

    public CartResponses() {
    }

    public CartResponses(List<CartResponse> products) {
        this.products = products;
    }

    public List<CartResponse> getProducts() {
        return products;
    }
}
