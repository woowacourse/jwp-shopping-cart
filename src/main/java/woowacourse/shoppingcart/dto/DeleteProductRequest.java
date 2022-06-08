package woowacourse.shoppingcart.dto;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<Long> getCartIds() {
        return products.stream()
                .map(CartIdRequest::getId)
                .collect(Collectors.toList());
    }
}
