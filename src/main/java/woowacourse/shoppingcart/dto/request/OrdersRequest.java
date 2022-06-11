package woowacourse.shoppingcart.dto.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class OrdersRequest {
    @NotNull
    private Long productId;
    @Min(0)
    private int quantity;

    public OrdersRequest() {
    }

    public OrdersRequest(Long productId, int quantity) {
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
