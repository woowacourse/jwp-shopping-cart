package woowacourse.shoppingcart.ui.dto;

public class CartItemQuantityRequest {

    private final Integer quantity;

    public CartItemQuantityRequest() {
        this(null);
    }

    public CartItemQuantityRequest(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getQuantity() {
        return quantity;
    }
}
