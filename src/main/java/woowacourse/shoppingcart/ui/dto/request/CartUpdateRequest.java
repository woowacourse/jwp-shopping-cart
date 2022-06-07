package woowacourse.shoppingcart.ui.dto.request;

import javax.validation.constraints.NotNull;

public class CartUpdateRequest {

    @NotNull
    private Long productId;

    @NotNull
    private Integer quantity;

    public CartUpdateRequest() {
    }

    public CartUpdateRequest(Long productId, Integer quantity) {
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
