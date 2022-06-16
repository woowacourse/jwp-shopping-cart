package woowacourse.shoppingcart.cart.dto;

public class CartItemAdditionRequest {

    private Long productId;

    private CartItemAdditionRequest() {
    }

    public CartItemAdditionRequest(final Long productId) {
        this.productId = productId;
    }

    public Long getProductId() {
        return productId;
    }
}
