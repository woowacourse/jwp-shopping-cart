package woowacourse.shoppingcart.dto;

public class UpdateCartItemRequest {

    private final Long id;
    private final int quantity;
    private final boolean checked;

    public UpdateCartItemRequest(Long cartId, int quantity, boolean checked) {
        this.id = cartId;
        this.quantity = quantity;
        this.checked = checked;
    }

    public Long getCartId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }

    public Object getChecked() {
        return checked;
    }
}
