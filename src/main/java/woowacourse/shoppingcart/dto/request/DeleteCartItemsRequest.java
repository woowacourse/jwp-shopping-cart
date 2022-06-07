package woowacourse.shoppingcart.dto.request;

import java.util.List;

public class DeleteCartItemsRequest {

    private List<Long> productIds;

    public DeleteCartItemsRequest() {
    }

    public DeleteCartItemsRequest(List<Long> productIds) {
        this.productIds = productIds;
    }

    public List<Long> getProductIds() {
        return productIds;
    }

    public void setProductIds(List<Long> productIds) {
        this.productIds = productIds;
    }
}
