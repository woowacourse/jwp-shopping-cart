package woowacourse.shoppingcart.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

import javax.validation.constraints.NotNull;

public class OrderDetailRequest {

    @NotNull
    private final long productId;
    @NotNull
    private final int quantity;

    @JsonCreator
    public OrderDetailRequest(final Long productId, final int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public long getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }
}
