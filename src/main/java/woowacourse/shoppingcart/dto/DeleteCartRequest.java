package woowacourse.shoppingcart.dto;

import java.util.List;

public class DeleteCartRequest {

    private List<Long> productIds;

    private DeleteCartRequest() {
    }

    public DeleteCartRequest(List<Long> productIds) {
        this.productIds = productIds;
    }

    public List<Long> getProductIds() {
        return productIds;
    }
}
