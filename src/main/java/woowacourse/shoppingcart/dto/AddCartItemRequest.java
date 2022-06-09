package woowacourse.shoppingcart.dto;

import javax.validation.constraints.NotNull;

public class AddCartItemRequest {

    @NotNull
    private Long productId;
    @NotNull
    private Long quantity;
    @NotNull
    private Boolean checked;

    private AddCartItemRequest() {
    }

    public AddCartItemRequest(Long productId, Long quantity, Boolean checked) {
        this.productId = productId;
        this.quantity = quantity;
        this.checked = checked;
    }

    public Long getProductId() {
        return productId;
    }

    public Long getQuantity() {
        return quantity;
    }

    public Boolean getChecked() {
        return checked;
    }
}
