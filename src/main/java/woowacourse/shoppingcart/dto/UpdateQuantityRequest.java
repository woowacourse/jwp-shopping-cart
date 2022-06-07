package woowacourse.shoppingcart.dto;

public class UpdateQuantityRequest {

    private int quantity;

    public UpdateQuantityRequest() {
    }

    public UpdateQuantityRequest(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }
}
