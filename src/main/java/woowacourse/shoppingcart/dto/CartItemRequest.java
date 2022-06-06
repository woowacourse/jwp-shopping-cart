package woowacourse.shoppingcart.dto;

public class CartItemRequest {

    private long id;
    private int quantity;

    public CartItemRequest(long id, int quantity) {
        this.id = id;
        this.quantity = quantity;
    }

    public long getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }
}
