package woowacourse.shoppingcart.domain;

import woowacourse.shoppingcart.dto.CartProductResponse;

import java.util.List;

public class CartProducts {

    private final List<CartProductResponse> products;

    public CartProducts(List<CartProductResponse> products) {
        this.products = products;
    }

    public List<CartProductResponse> getProducts() {
        return products;
    }
}
