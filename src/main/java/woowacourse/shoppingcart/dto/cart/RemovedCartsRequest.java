package woowacourse.shoppingcart.dto.cart;

import java.util.List;

public class RemovedCartsRequest {

    private List<Integer> productIds;

    public RemovedCartsRequest() {
    }

    public RemovedCartsRequest(List<Integer> productsIds) {
        this.productIds = productsIds;
    }

    public List<Integer> getProductIds() {
        return productIds;
    }
}
