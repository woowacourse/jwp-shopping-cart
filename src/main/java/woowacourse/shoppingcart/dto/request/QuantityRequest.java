package woowacourse.shoppingcart.dto.request;

public class QuantityRequest {

    private int quantity;

    public QuantityRequest() {
    }

    public QuantityRequest(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }
}
