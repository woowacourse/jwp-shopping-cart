package woowacourse.shoppingcart.dto;

import javax.validation.constraints.Min;

public class CartItemSaveRequest {

    private final Long productId;

    @Min(value = 0)
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
