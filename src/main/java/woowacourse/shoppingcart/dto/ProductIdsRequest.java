package woowacourse.shoppingcart.dto;

import java.util.List;

public class ProductIdsRequest {

    private List<Long> ids;

    private ProductIdsRequest() {

    }

    public ProductIdsRequest(List<Long> ids) {
        this.ids = ids;
    }

    public List<Long> getIds() {
        return ids;
    }
}
