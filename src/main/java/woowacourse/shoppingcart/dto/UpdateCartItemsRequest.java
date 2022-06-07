package woowacourse.shoppingcart.dto;

import java.util.List;

public class UpdateCartItemsRequest {
    private List<UpdateCartItemRequest> products;

    public UpdateCartItemsRequest() {
    }

    public UpdateCartItemsRequest(List<UpdateCartItemRequest> updateCartItemRequest) {
        this.products = updateCartItemRequest;
    }

    public List<UpdateCartItemRequest> getProducts() {
        return products;
    }
}
