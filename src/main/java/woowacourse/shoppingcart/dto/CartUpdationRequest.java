package woowacourse.shoppingcart.dto;

public class CartUpdationRequest {

    private int quantity;

    private CartUpdationRequest() {
    }

    public CartUpdationRequest(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }
}
