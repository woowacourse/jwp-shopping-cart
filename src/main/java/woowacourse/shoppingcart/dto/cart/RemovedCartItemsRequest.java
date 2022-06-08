package woowacourse.shoppingcart.dto.cart;

import java.util.List;

public class RemovedCartItemsRequest {

    private List<Integer> productIds;

    public RemovedCartItemsRequest() {
    }

    public RemovedCartItemsRequest(List<Integer> productsIds) {
        this.productIds = productsIds;
    }

    public List<Integer> getProductIds() {
        return productIds;
    }
}
