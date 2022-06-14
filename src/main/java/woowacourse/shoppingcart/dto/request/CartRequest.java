package woowacourse.shoppingcart.dto.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class CartRequest {

    @Positive
    private Long productId;

    @Positive
    private Integer quantity;

    @NotNull
    private Boolean checked;

    private CartRequest() {
    }

    public CartRequest(Long productId, Integer quantity, Boolean checked) {
        this.productId = productId;
        this.quantity = quantity;
        this.checked = checked;
    }

    public Long getProductId() {
        return productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Boolean getChecked() {
        return checked;
    }
}
