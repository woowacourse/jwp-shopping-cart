package woowacourse.shoppingcart.ui.cart.dto;

public class CartItemRegisterRequest {

    private Long productId;

    public CartItemRegisterRequest() {
    }

    public CartItemRegisterRequest(final Long productId) {
        this.productId = productId;
    }

    public Long getProductId() {
        return productId;
    }
}
