package woowacourse.shoppingcart.dto;

public class UpdateCartItemRequest {

    private int quantity;

    private UpdateCartItemRequest() {
    }

    public UpdateCartItemRequest(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }
}
