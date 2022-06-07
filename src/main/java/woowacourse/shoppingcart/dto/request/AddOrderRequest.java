package woowacourse.shoppingcart.dto.request;

import java.util.List;

public class AddOrderRequest {

    private List<Long> productIds;

    public AddOrderRequest() {
    }

    public AddOrderRequest(List<Long> productIds) {
        this.productIds = productIds;
    }

    public List<Long> getProductIds() {
        return productIds;
    }

    public void setProductIds(List<Long> productIds) {
        this.productIds = productIds;
    }
}
