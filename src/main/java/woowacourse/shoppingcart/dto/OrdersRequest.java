package woowacourse.shoppingcart.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

public class OrdersRequest {

    @NotBlank
    private final List<OrderRequest> order;

    @JsonCreator
    public OrdersRequest(List<OrderRequest> order) {
        this.order = order;
    }

    public List<OrderRequest> getOrder() {
        return order;
    }

    public List<Long> getIds() {
        return this.order.stream()
                .map(OrderRequest::getId)
                .collect(Collectors.toList());
    }

    public List<Integer> getQuantities() {
        return this.order.stream()
                .map(OrderRequest::getQuantity)
                .collect(Collectors.toList());
    }
}
