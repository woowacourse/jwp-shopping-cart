package woowacourse.shoppingcart.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class OrderRequest {
    @NotNull
    private final Long productId;
    @Min(0)
    private final int quantity;

    public OrderRequest(final Long productId, final int quantity) {
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
