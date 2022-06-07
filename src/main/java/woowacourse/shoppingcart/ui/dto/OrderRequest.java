package woowacourse.shoppingcart.ui.dto;

import java.util.List;

public class OrderRequest {

    private List<OrderDetailRequest> order;

    private OrderRequest() {
    }

    public OrderRequest(List<OrderDetailRequest> order) {
        this.order = order;
    }

    public List<OrderDetailRequest> getOrder() {
        return order;
    }
}
