package woowacourse.shoppingcart.dto;

public class CartItemSaveRequest {

    private final Long productId;
    private final Integer quantity;

    public CartItemSaveRequest() {
        this(null, null);
    }

    public CartItemSaveRequest(Long productId, Integer quantity) {
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
