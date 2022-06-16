package woowacourse.shoppingcart.cart.dto;

import java.util.Objects;
import javax.validation.constraints.NotNull;

public class QuantityChangingRequest {

    @NotNull
    private Integer quantity;

    private QuantityChangingRequest() {
    }

    public QuantityChangingRequest(final Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getQuantity() {
        return quantity;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final QuantityChangingRequest that = (QuantityChangingRequest) o;
        return Objects.equals(quantity, that.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(quantity);
    }
}
