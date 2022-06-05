package woowacourse.shoppingcart.dto.cartitem;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class CartItemSaveRequest {

    @NotNull
    private Long productId;

    @NotNull
    @Min(1)
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
