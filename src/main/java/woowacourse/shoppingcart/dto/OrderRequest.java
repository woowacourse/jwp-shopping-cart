package woowacourse.shoppingcart.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

import javax.validation.constraints.NotNull;
import java.util.List;

public class OrderRequest {

    @NotNull
    private final List<OrderDetailRequest> order;

    @JsonCreator
    public OrderRequest(List<OrderDetailRequest> order) {
        this.order = order;
    }

    public List<OrderDetailRequest> getOrder() {
        return order;
    }
}
