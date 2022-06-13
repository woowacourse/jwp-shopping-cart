package woowacourse.shoppingcart.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

public class CartItemRequest {

    @NotNull
    private Long productId;

    @NotNull
    @PositiveOrZero(message = "Invalid Quantity")
    private Integer quantity;

    public CartItemRequest() {
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
