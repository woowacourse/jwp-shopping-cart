package woowacourse.shoppingcart.dto;

public class CartItemRequest {

    private int quantity;

    public CartItemRequest() {}

    public CartItemRequest(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }
}
