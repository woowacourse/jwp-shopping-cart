package woowacourse.shoppingcart.dto.cartItem;

import java.util.List;

public class CartItemDeleteRequest {

    private List<Long> productIds;

    public CartItemDeleteRequest() {
    }

    public CartItemDeleteRequest(List<Long> productIds) {
        this.productIds = productIds;
    }

    public List<Long> getProductIds() {
        return productIds;
    }
}
