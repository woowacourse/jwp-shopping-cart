package woowacourse.shoppingcart.ui.dto;

public class CartItemRequest {

    private final Long productId;
    private final Integer quantity;

    public CartItemRequest() {
        this(null, null);
    }

    public CartItemRequest(Long productId, Integer quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public Long getProductId() {
        return productId;
    }

    public Integer getQuantity() {
        return quantity;
    }
}
