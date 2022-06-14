package woowacourse.shoppingcart.dto;

import java.util.List;

public class ProductsRequest {

    private List<Long> productIds;

    public ProductsRequest() {
    }

    public ProductsRequest(List<Long> productIds) {
        this.productIds = productIds;
    }

    public List<Long> getProductIds() {
        return productIds;
    }
}
