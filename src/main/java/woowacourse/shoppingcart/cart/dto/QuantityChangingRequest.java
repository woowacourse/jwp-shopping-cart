package woowacourse.shoppingcart.cart.dto;

import java.util.Objects;

public class QuantityChangingRequest {

    private int quantity;

    private QuantityChangingRequest() {
    }

    public QuantityChangingRequest(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
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
        return quantity == that.quantity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(quantity);
    }
}
