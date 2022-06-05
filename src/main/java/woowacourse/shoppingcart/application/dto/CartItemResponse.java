package woowacourse.shoppingcart.application.dto;

import woowacourse.shoppingcart.domain.cart.CartItem;

public class CartItemResponse {

    private final Long id;
    private final Integer quantity;
    private final ProductResponse productResponse;

    public CartItemResponse() {
        this(null, null, null);
    }

    public CartItemResponse(Long id, Integer quantity,
        ProductResponse productResponse) {
        this.id = id;
        this.quantity = quantity;
        this.productResponse = productResponse;
    }

    public static CartItemResponse from(CartItem cartItem) {
        return new CartItemResponse(
            cartItem.getId(),
            cartItem.getQuantity(),
            ProductResponse.from(cartItem.getProduct())
        );
    }

    public Long getId() {
        return id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public ProductResponse getProductResponse() {
        return productResponse;
    }
}
