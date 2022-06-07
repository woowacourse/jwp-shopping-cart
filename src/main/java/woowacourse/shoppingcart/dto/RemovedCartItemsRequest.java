package woowacourse.shoppingcart.dto;

import java.util.List;

public class RemovedCartItemsRequest {

    private List<Integer> ids;

    public RemovedCartItemsRequest() {
    }

    public RemovedCartItemsRequest(List<Integer> productsIds) {
        this.ids = productsIds;
    }

    public List<Integer> getIds() {
        return ids;
    }
}
