package woowacourse.shoppingcart.dto;

public class UpdateCartItemRequest {

    private Long id;
    private int quantity;
    private boolean checked;

    public UpdateCartItemRequest() {
    }

    public UpdateCartItemRequest(final Long cartId, final int quantity, final boolean checked) {
        this.id = cartId;
        this.quantity = quantity;
        this.checked = checked;
    }

    public Long getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }

    public Object getChecked() {
        return checked;
    }
}
