package woowacourse.shoppingcart.dto;

import java.util.List;

public class UpdateCartItemRequests {
    private List<UpdateCartItemRequest> products;

    public UpdateCartItemRequests() {
    }

    public UpdateCartItemRequests(List<UpdateCartItemRequest> updateCartItemRequest) {
        this.products = updateCartItemRequest;
    }

    public List<UpdateCartItemRequest> getProducts() {
        return products;
    }
}
