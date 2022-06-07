package woowacourse.shoppingcart.dto;

import javax.validation.constraints.NotNull;

public class CartItemRequest {

    @NotNull
    private Long productId;

    @NotNull
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
