package woowacourse.shoppingcart.dto;

import java.util.List;

public class DeleteProductRequest {

    private List<CartIdRequest> products;

    public DeleteProductRequest() {
    }

    public DeleteProductRequest(List<CartIdRequest> products) {
        this.products = products;
    }

    public List<CartIdRequest> getProducts() {
        return products;
    }
}
