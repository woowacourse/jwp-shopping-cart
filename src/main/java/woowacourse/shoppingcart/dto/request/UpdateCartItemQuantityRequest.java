package woowacourse.shoppingcart.dto.request;

public class UpdateCartItemQuantityRequest {

    private Integer quantity;

    public UpdateCartItemQuantityRequest() {
    }

    public UpdateCartItemQuantityRequest(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
