package woowacourse.shoppingcart.dto.order;

import java.util.ArrayList;
import java.util.List;

public class OrderSaveRequest {
    private List<Long> productIds;

    public OrderSaveRequest() {
    }

    public OrderSaveRequest(List<Long> productIds) {
        this.productIds = new ArrayList<>(productIds);
    }

    public List<Long> getProductIds() {
        return new ArrayList<>(productIds);
    }
}
