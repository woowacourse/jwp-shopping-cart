package woowacourse.shoppingcart.dto;

import java.util.List;

public class UpdateCartRequests {

    private List<UpdateCartRequest> products;

    public UpdateCartRequests() {
    }

    public UpdateCartRequests(List<UpdateCartRequest> products) {
        this.products = products;
    }

    public List<UpdateCartRequest> getProducts() {
        return products;
    }
}
