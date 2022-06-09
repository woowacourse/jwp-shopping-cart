package woowacourse.shoppingcart.dto.cart;

public class CartItemResponse {

    private final Long productId;
    private final int quantity;

    public CartItemResponse(final Long id, final int quantity) {
        this.productId = id;
        this.quantity = quantity;
    }

    public Long getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }
}
