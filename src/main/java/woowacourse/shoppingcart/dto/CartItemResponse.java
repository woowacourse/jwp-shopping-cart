package woowacourse.shoppingcart.dto;

import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.Product;

public class CartItemResponse {
    private ProductResponse product;
    private int quantity;

    private CartItemResponse() {
    }

    public CartItemResponse(ProductResponse product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public static CartItemResponse from(CartItem cartItem) {
        return new CartItemResponse(ProductResponse.from(cartItem.getProduct()), cartItem.getQuantity());
    }

    public static CartItemResponse of(Product product, int quantity) {
        return new CartItemResponse(ProductResponse.from(product), quantity);
    }

    public ProductResponse getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }
}
