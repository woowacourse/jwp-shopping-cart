package woowacourse.shoppingcart.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class CartProductRequest {

    @NotNull
    private Long productId;
    @NotNull
    @Positive
    private Long quantity;
    private boolean checked;

    private CartProductRequest() {
    }

    public CartProductRequest(Long productId, Long quantity, boolean checked) {
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

    public boolean isChecked() {
        return checked;
    }
}
