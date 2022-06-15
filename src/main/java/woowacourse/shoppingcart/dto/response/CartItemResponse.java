package woowacourse.shoppingcart.dto.response;

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

    public CartItemResponse(ProductResponse product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public ProductResponse getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }
}
