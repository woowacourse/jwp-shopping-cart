package woowacourse.shoppingcart.dto;

public class CartItemRequest {

    private final int id;
    private final int quantity;

    public CartItemRequest(int id, int quantity) {
        this.id = id;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }
}
