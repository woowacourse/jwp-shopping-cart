package woowacourse.shoppingcart.dto.cart;

public class CartSetRequest {

    private int quantity;

    public CartSetRequest() {
    }

    public CartSetRequest(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }
}
