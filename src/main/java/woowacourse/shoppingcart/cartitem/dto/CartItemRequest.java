package woowacourse.shoppingcart.cartitem.dto;

import javax.validation.constraints.Min;

public class CartItemRequest {
    private Long productId;

    @Min(message = "5002", value = 0)
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
