package woowacourse.shoppingcart.dto;

import java.util.List;

public class ModifyProductRequests {

    private List<ModifyProductRequest> cartItems;

    private ModifyProductRequests() {
    }

    public ModifyProductRequests(List<ModifyProductRequest> cartItems) {
        this.cartItems = cartItems;
    }

    public List<ModifyProductRequest> getCartItems() {
        return cartItems;
    }
}
