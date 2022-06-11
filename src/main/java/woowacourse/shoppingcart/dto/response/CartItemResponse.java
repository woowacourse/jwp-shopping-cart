package woowacourse.shoppingcart.dto.response;

import woowacourse.shoppingcart.domain.CartItem;

public class CartItemResponse {

    private ProductResponse product;
    private Integer quantity;

    public CartItemResponse() {
    }

    public CartItemResponse(CartItem cartItem) {
        this.product = new ProductResponse(cartItem.getProduct());
        this.quantity = cartItem.getQuantity();
    }

    public CartItemResponse(ProductResponse product, Integer quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public ProductResponse getProduct() {
        return product;
    }

    public Integer getQuantity() {
        return quantity;
    }
}
