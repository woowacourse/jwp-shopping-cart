package woowacourse.shoppingcart.dto;

import woowacourse.shoppingcart.domain.Cart;

public class CartResponse {

    private ProductResponse product;
    private int quantity;

    private CartResponse() {
    }

    public CartResponse(ProductResponse product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public CartResponse(Cart cart) {
        this(new ProductResponse(cart.getProduct()), cart.getQuantity());
    }

    public ProductResponse getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }
}
