package woowacourse.shoppingcart.dto;

public class ChangeCartItemQuantityRequest {
    private int quantity;

    public ChangeCartItemQuantityRequest() {
    }

    public ChangeCartItemQuantityRequest(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }
}
