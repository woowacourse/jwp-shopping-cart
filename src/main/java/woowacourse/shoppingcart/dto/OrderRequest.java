package woowacourse.shoppingcart.dto;

import java.util.List;
import javax.validation.constraints.NotNull;

public class OrderRequest {

    @NotNull
    private List<Long> productIds;

    public OrderRequest() {
    }

    public OrderRequest(List<Long> productIds) {
        this.productIds = productIds;
    }

    public List<Long> getProductIds() {
        return productIds;
    }
}
