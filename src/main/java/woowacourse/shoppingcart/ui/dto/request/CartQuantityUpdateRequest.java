package woowacourse.shoppingcart.ui.dto.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class CartQuantityUpdateRequest {
    @NotNull
    @Positive
    private Long productId;
    @NotNull
    @Positive
    private Integer quantity;

    public CartQuantityUpdateRequest() {
    }

    public CartQuantityUpdateRequest(final Long productId, final Integer quantity) {
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
