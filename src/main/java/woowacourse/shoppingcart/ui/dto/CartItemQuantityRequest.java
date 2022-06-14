package woowacourse.shoppingcart.ui.dto;

import woowacourse.shoppingcart.application.dto.CartItemQuantityUpdateRequest;

public class CartItemQuantityRequest {

    private int quantity;

    public CartItemQuantityRequest() {
    }

    public CartItemQuantityRequest(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public CartItemQuantityUpdateRequest toServiceRequest(Long customerId, Long productId, int quantity) {
        return new CartItemQuantityUpdateRequest(customerId, productId, quantity);
    }
}
