package woowacourse.shoppingcart.dto.cart;

public class UpdateCartItemRequest {

    private Long productId;
    private Integer quantity;

    public UpdateCartItemRequest() {
    }

    public UpdateCartItemRequest(final Long productId, final Integer quantity) {
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
