package woowacourse.shoppingcart.dto;

import woowacourse.shoppingcart.domain.CartItem;

public class CartItemResponse {

    private ProductResponse product;
    private int quantity;

    public CartItemResponse() {
    }

    public CartItemResponse(CartItem cartItem) {
        this.product = new ProductResponse(cartItem.getProduct());
        this.quantity = cartItem.getQuantity();
    }

    public ProductResponse getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }
}
