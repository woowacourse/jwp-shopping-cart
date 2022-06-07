package woowacourse.shoppingcart.dto;

import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.domain.Product;

public class CartResponse {
    private ProductResponse product;
    private int quantity;

    private CartResponse() {
    }

    public CartResponse(ProductResponse product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public static CartResponse from(Cart cart) {
        return new CartResponse(ProductResponse.from(cart.getProduct()), cart.getQuantity());
    }

    public static CartResponse of(Product product, int quantity) {
        return new CartResponse(ProductResponse.from(product), quantity);
    }

    public ProductResponse getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }
}
