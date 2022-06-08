package woowacourse.shoppingcart.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class OrderRequest {
    @NotNull
    private Long cart_id;
    @Min(0)
    private int quantity;

    private OrderRequest() {
    }

    public OrderRequest(Long cart_id, int quantity) {
        this.cart_id = cart_id;
        this.quantity = quantity;
    }

    public Long getCart_id() {
        return cart_id;
    }

    public int getQuantity() {
        return quantity;
    }
}
