package woowacourse.shoppingcart.dto;

import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.domain.Product;

public class CartItemResponse {
    private Long id;
    private Product product;

    public CartItemResponse() {
    }

    public CartItemResponse(Cart cart) {
        this.id = cart.getId();
        this.product = cart.getProduct();
    }

    public Long getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }
}
