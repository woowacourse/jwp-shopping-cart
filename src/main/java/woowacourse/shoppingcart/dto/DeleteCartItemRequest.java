package woowacourse.shoppingcart.dto;

import java.util.List;
import java.util.stream.Collectors;

public class DeleteCartItemRequest {

    private List<DeleteCartItemElement> products;

    public DeleteCartItemRequest() {
    }

    public DeleteCartItemRequest(List<DeleteCartItemElement> products) {
        this.products = products;
    }

    public List<DeleteCartItemElement> getProducts() {
        return products;
    }

    public List<Long> getIds() {
        return products.stream()
                .map(DeleteCartItemElement::getId)
                .collect(Collectors.toList());
    }
}
