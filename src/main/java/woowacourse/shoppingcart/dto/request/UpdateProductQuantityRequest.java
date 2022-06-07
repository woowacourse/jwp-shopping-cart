package woowacourse.shoppingcart.dto.request;

public class UpdateProductQuantityRequest {

    private int quantity;

    public UpdateProductQuantityRequest() {
    }

    public UpdateProductQuantityRequest(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
