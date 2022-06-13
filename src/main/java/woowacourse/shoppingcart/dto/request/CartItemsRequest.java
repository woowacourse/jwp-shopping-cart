package woowacourse.shoppingcart.dto.request;

import java.util.List;

public class CartItemsRequest {
    private List<Long> productIds;

    public CartItemsRequest() {
    }

    public CartItemsRequest(List<Long> productIds) {
        this.productIds = List.copyOf(productIds);
    }

    public List<Long> getProductIds() {
        return productIds;
    }
}
