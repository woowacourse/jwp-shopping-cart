package woowacourse.shoppingcart.ui.cart.dto.request;

public class CartItemUpdateRequest {

    private Long productId;
    private int quantity;

    public CartItemUpdateRequest() {
    }

    public CartItemUpdateRequest(final Long productId, final int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public Long getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }
}
