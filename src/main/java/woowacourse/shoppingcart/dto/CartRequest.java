package woowacourse.shoppingcart.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class CartRequest {

    @NotBlank
    @JsonProperty(value = "product_id")
    private Long productId;

    @Min(value = 1)
    private int quantity;

    private CartRequest() {
    }

    public CartRequest(final Long productId, final int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public Long getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }
}
