package woowacourse.shoppingcart.dto.request;

import java.util.List;
import javax.validation.constraints.NotNull;

public class OrderRequest {
    @NotNull
    private List<Long> productIds;

    private OrderRequest() {
    }

    public OrderRequest(List<Long> productIds) {
        this.productIds = productIds;
    }

    public List<Long> getProductIds() {
        return productIds;
    }
}
