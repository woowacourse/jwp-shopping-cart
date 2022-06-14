package woowacourse.shoppingcart.dto;

import java.util.List;

public class ProductIdsRequest {

    private List<Long> productIds;

    private ProductIdsRequest() {

    }

    public ProductIdsRequest(List<Long> productIds) {
        this.productIds = productIds;
    }

    public List<Long> getProductIds() {
        return productIds;
    }
}
