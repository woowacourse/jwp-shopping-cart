package woowacourse.shoppingcart.dto;

public class CartItemChangeQuantityRequest {
    private int quantity;

    public CartItemChangeQuantityRequest() {
    }

    public CartItemChangeQuantityRequest(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }
}
