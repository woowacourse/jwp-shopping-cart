package woowacourse.shoppingcart.dto;

import java.util.ArrayList;
import java.util.List;

public class CartDeleteRequest {
    private List<Long> productIds;

    public CartDeleteRequest() {
    }

    public CartDeleteRequest(List<Long> productIds) {
        this.productIds = new ArrayList<>(productIds);
    }

    public List<Long> getProductIds() {
        return new ArrayList<>(productIds);
    }
}
