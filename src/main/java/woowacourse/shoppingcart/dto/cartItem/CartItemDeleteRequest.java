package woowacourse.shoppingcart.dto.cartItem;

import java.util.List;
import javax.validation.constraints.NotNull;

public class CartItemDeleteRequest {

    @NotNull
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
