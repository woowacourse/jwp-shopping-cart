package woowacourse.shoppingcart.order.dto;

import java.util.Objects;
import javax.validation.constraints.NotNull;

public class OrderCreationRequest {

    @NotNull
    private Long productId;

    private OrderCreationRequest() {
    }

    public OrderCreationRequest(final Long productId) {
        this.productId = productId;
    }

    public Long getProductId() {
        return productId;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final OrderCreationRequest that = (OrderCreationRequest) o;
        return Objects.equals(productId, that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId);
    }
}
