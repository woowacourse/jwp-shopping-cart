package woowacourse.shoppingcart.dto;

import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.domain.Product;

public class CartResponse {
    private ProductResponse productResponse;
    private int quantity;

    private CartResponse() {
    }

    public CartResponse(ProductResponse productResponse, int quantity) {
        this.productResponse = productResponse;
        this.quantity = quantity;
    }

    public static CartResponse from(Cart cart) {
        return new CartResponse(ProductResponse.from(cart.getProduct()), cart.getQuantity());
    }

    public static CartResponse of(Product product, int quantity) {
        return new CartResponse(ProductResponse.from(product), quantity);
    }

    public ProductResponse getProductResponse() {
        return productResponse;
    }

    public int getQuantity() {
        return quantity;
    }
}
