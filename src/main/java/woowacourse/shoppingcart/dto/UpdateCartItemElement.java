package woowacourse.shoppingcart.dto;

public class UpdateCartItemElement {

    private Long id;
    private int quantity;
    private boolean checked;

    public UpdateCartItemElement() {
    }

    public UpdateCartItemElement(Long id, int quantity, boolean checked) {
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
