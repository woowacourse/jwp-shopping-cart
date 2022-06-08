package woowacourse.shoppingcart.dto.response;

import woowacourse.shoppingcart.domain.CartItem;

public class CartItemResponse {
    private Long cartItemId;
    private ProductResponse product;
    private Integer quantity;

    public CartItemResponse() {
    }

    public CartItemResponse(Long cartItemId, ProductResponse product, Integer quantity) {
        this.cartItemId = cartItemId;
        this.product = product;
        this.quantity = quantity;
    }

    public CartItemResponse(CartItem cartItem) {
        this(cartItem.getId(), new ProductResponse(cartItem.getProduct()), cartItem.getQuantity());
    }

    public Long getCartItemId() {
        return cartItemId;
    }

    public ProductResponse getProduct() {
        return product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return "CartItemResponse{" +
                "cartItemId=" + cartItemId +
                ", product=" + product +
                ", quantity=" + quantity +
                '}';
    }
}
