package woowacourse.shoppingcart.dto;

import javax.validation.constraints.NotNull;

public class OrderDetailRequest {

    @NotNull
    private final long id;
    @NotNull
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
