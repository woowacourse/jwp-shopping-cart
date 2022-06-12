package woowacourse.shoppingcart.dto;

import woowacourse.shoppingcart.domain.Quantity;

import java.util.List;
import java.util.stream.Collectors;

public class UpdateCartItemRequest {

    List<UpdateCartItemElement> cartItems;

    public UpdateCartItemRequest() {
    }

    public UpdateCartItemRequest(List<UpdateCartItemElement> cartItems) {
        this.cartItems = cartItems;
    }

    public List<UpdateCartItemElement> getCartItems() {
        return cartItems;
    }

    public List<Long> generateCartIds() {
        return cartItems.stream()
                .map(UpdateCartItemElement::getId)
                .collect(Collectors.toList());
    }

    public List<Integer> generateQuantities() {
        return cartItems.stream()
                .map(UpdateCartItemElement::getQuantity)
                .collect(Collectors.toList());
    }

    public List<Boolean> generateChecked() {
        return cartItems.stream()
                .map(UpdateCartItemElement::getChecked)
                .collect(Collectors.toList());
    }
}
