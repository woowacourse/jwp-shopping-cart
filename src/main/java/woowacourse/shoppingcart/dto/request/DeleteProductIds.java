package woowacourse.shoppingcart.dto.request;

import java.util.List;
import javax.validation.constraints.NotNull;

public class DeleteProductIds {

    private List<@NotNull Long> productIds;

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
