package woowacourse.shoppingcart.dto;

public class CartUpdateRequest {

    private Long productId;
    private Integer quantity;

    public CartUpdateRequest() {
    }

    public CartUpdateRequest(final Long productId, final Integer quantity) {
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
