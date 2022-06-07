package woowacourse.shoppingcart.dto;

public class CartQuantityRequest {

    private int quantity;

    public CartQuantityRequest() {}

    public CartQuantityRequest(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }
}
