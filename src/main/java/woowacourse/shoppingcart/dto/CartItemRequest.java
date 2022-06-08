package woowacourse.shoppingcart.dto;

public class CartItemRequest {

    private Long productId;
    private int quantity;

    private CartItemRequest() {
    }

    public CartItemRequest(Long productId, int quantity) {
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
