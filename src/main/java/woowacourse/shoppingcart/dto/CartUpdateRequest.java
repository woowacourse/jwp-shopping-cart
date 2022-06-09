package woowacourse.shoppingcart.dto;

public class CartUpdateRequest {
    private int quantity;

    public CartUpdateRequest() {
    }

    public CartUpdateRequest(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }
}
