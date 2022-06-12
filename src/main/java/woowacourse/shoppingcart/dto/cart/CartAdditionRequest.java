package woowacourse.shoppingcart.dto.cart;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class CartAdditionRequest {

    @NotNull(message = "값이 존재하지 않습니다.")
    private Long productId;
    @Min(value = 1, message = "양수여야 합니다.")
    private int quantity;

    private CartAdditionRequest() {
    }

    public CartAdditionRequest(Long productId, int quantity) {
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
