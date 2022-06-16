package woowacourse.shoppingcart.dto;

public class QuantityUpdateRequest {
    private int quantity;

    private QuantityUpdateRequest() {
    }

    public QuantityUpdateRequest(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }
}
