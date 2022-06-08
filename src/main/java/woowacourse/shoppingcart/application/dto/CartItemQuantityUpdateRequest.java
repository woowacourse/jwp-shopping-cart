package woowacourse.shoppingcart.application.dto;

public class CartItemQuantityUpdateRequest {

    private Long customerId;
    private Long productId;
    private int quantity;

    public CartItemQuantityUpdateRequest(Long customerId, Long productId, int quantity) {
        this.customerId = customerId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public Long getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }
}
