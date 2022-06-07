package woowacourse.shoppingcart.dto.cartItem;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class CartItemAddRequest {
    @NotNull
    private Long productId;
    @Min(1)
    private int quantity;

    public CartItemAddRequest() {
    }

    public CartItemAddRequest(Long productId, int quantity) {
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
