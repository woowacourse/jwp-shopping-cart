package woowacourse.shoppingcart.application.dto;

import woowacourse.shoppingcart.domain.cart.CartItem;

public class CartItemResponse {

    private final Long id;
    private final ProductResponse productResponse;

    public CartItemResponse() {
        this(null, null);
    }

    public CartItemResponse(Long id, ProductResponse productResponse) {
        this.id = id;
        this.productResponse = productResponse;
    }

    public static CartItemResponse from(CartItem cartItem) {
        return new CartItemResponse(
            cartItem.getId(),
            ProductResponse.from(cartItem.getProduct())
        );
    }

    public Long getId() {
        return id;
    }

    public ProductResponse getProductResponse() {
        return productResponse;
    }
}
