package woowacourse.shoppingcart.dto.request;

import java.util.List;

public class DeleteProductIds {

    private List<Long> productIds;

    public DeleteProductIds() {
    }

    public DeleteProductIds(List<Long> productIds) {
        this.productIds = productIds;
    }

    public List<Long> getProductIds() {
        return productIds;
    }

    public void setProductIds(List<Long> productIds) {
        this.productIds = productIds;
    }
}
