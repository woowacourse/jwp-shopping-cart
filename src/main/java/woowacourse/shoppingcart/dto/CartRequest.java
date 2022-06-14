package woowacourse.shoppingcart.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class CartRequest {

    @NotNull(groups = {Request.id.class})
    private Long productId;

    @Positive(groups = {Request.allProperties.class})
    private int quantity;

    private CartRequest() {
    }

    public CartRequest(Long productId, int quantity) {
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
