package woowacourse.shoppingcart.cart.application.dto.request;

import java.util.List;

public class CartDeleteRequest {

    private List<Long> productIds;

    public CartDeleteRequest() {
    }

    public CartDeleteRequest(List<Long> productIds) {
        this.productIds = productIds;
    }

    public List<Long> getProductIds() {
        return productIds;
    }
}
