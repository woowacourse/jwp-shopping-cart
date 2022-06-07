package woowacourse.shoppingcart.dto.cartItem;

import javax.validation.constraints.NotNull;

public class CartItemAddRequest {

    @NotNull
    private Long productId;

    @NotNull
    private int quantity;

    private CartItemAddRequest() {
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
