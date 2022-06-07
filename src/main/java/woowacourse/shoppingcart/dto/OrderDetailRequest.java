package woowacourse.shoppingcart.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class OrderDetailRequest {

    @NotNull
    private final long id;
    @Min(0)
    private final int quantity;

    public OrderDetailRequest(final Long id, final int quantity) {
        this.id = id;
        this.quantity = quantity;
    }

    public long getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }
}
