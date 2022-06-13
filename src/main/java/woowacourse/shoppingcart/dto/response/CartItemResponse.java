package woowacourse.shoppingcart.dto.response;

import woowacourse.shoppingcart.domain.CartItem;

public class CartItemResponse {
    private ProductResponse product;
    private int quantity;

    public CartItemResponse() {
    }

    private CartItemResponse(ProductResponse product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public static CartItemResponse from(CartItem cartItem) {
        ProductResponse productResponse = ProductResponse.from(cartItem.getProduct());
        return new CartItemResponse(productResponse, cartItem.getQuantity());
    }

    public ProductResponse getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }
}
