package woowacourse.shoppingcart.dto;

public class ModifyProductRequest {

    private Long id;
    private Long quantity;
    private boolean checked;

    private ModifyProductRequest() {
    }

    public ModifyProductRequest(Long id, Long quantity, boolean checked) {
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
