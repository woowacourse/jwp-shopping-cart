package woowacourse.shoppingcart.ui.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class CartItemRequest {

    @NotNull
    @Min(0)
    private final Long productId;

    @Min(0)
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
