package woowacourse.shoppingcart.cart.dto;

import java.util.Objects;
import javax.validation.constraints.Positive;

public class QuantityChangingRequest {

    @Positive(message = "수량이 유효하지 않습니다.")
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
