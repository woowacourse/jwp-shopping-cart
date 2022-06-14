package woowacourse.shoppingcart.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

public class AddCartItemRequest {

    private Long productId;

    @NotNull
    @PositiveOrZero(message = "5002")
    private int quantity;

    public AddCartItemRequest() {
    }

    public AddCartItemRequest(Long productId, int quantity) {
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
