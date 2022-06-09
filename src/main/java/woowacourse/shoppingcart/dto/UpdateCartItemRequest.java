package woowacourse.shoppingcart.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class UpdateCartItemRequest {

    @NotNull
    private Long id;
    @Min(1)
    private Long quantity;
    @NotNull
    private Boolean checked;

    private UpdateCartItemRequest() {
    }

    public UpdateCartItemRequest(Long id, Long quantity, Boolean checked) {
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

    public Boolean getChecked() {
        return checked;
    }
}
