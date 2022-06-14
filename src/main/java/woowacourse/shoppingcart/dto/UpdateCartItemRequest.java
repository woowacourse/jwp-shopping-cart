package woowacourse.shoppingcart.dto;

public class UpdateCartItemRequest {

    private Long id;
    private Long quantity;
    private boolean checked;

    private UpdateCartItemRequest() {
    }

    public UpdateCartItemRequest(Long id, Long quantity, boolean checked) {
        this.id = id;
        this.quantity = quantity;
        this.checked = checked;
    }

    public Long getId() {
        return id;
    }

    public Long getQuantity() {
        return quantity;
    }

    public boolean isChecked() {
        return checked;
    }
}
