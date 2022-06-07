package woowacourse.shoppingcart.dto;

import java.util.Map;

public class DeleteCartItemRequest {

    private Map<String, Long> products;

    public DeleteCartItemRequest() {
    }

    public DeleteCartItemRequest(Map<String, Long> products) {
        this.products = products;
    }

    public Map<String, Long> getProducts() {
        return products;
    }
}
