package woowacourse.shoppingcart.dto;

public class AddCartItemRequest {

    private final Long id;
    private final int quantity;
    private final boolean checked;

    public AddCartItemRequest(Long id, int quantity, boolean checked) {
        this.id = id;
        this.quantity = quantity;
        this.checked = checked;
    }

    public Long getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }

    public boolean getChecked() {
        return checked;
    }
}
