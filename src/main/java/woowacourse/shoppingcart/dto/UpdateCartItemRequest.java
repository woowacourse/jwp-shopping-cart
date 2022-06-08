package woowacourse.shoppingcart.dto;

import java.util.List;
import java.util.stream.Collectors;

public class UpdateCartItemRequest {

    List<UpdateCartItemElement> products;

    public UpdateCartItemRequest() {
    }

    public UpdateCartItemRequest(List<UpdateCartItemElement> products) {
        this.products = products;
    }

    public List<UpdateCartItemElement> getProducts() {
        return products;
    }

    public List<Long> getCartIds() {
        return products.stream()
                .map(UpdateCartItemElement::getId)
                .collect(Collectors.toList());
    }

    public List<Integer> getQuantities() {
        return products.stream()
                .map(UpdateCartItemElement::getQuantity)
                .collect(Collectors.toList());
    }

    public List<Boolean> generateChecked() {
        return products.stream()
                .map(UpdateCartItemElement::getChecked)
                .collect(Collectors.toList());
    }
}
