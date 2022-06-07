package woowacourse.shoppingcart.dto;

import woowacourse.shoppingcart.domain.Cart;

public class CartItemResponse {

    private final ProductResponse product;
    private final Integer quantity;

    public CartItemResponse(ProductResponse product, Integer quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public static CartItemResponse from(Cart cart) {
        return new CartItemResponse(ProductResponse.from(cart.getProduct(), true), cart.getQuantity());
    }

    public ProductResponse getProduct() {
        return product;
    }

    public Integer getQuantity() {
        return quantity;
    }
}
