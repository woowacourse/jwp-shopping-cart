package woowacourse.shoppingcart.dto;

public class CartItemSaveRequest {

    private Long productId;
    private Integer quantity;

    private CartItemSaveRequest() {
    }

    public CartItemSaveRequest(final Long productId, final Integer quantity) {
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
