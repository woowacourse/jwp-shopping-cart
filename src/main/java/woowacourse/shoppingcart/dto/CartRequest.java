package woowacourse.shoppingcart.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CartRequest {

    @NotNull(message = "product id를 입력하세요.")
    private Long productId;

    private CartRequest() {
    }

    public CartRequest(final Long productId) {
        this.productId = productId;
    }

    public Long getProductId() {
        return productId;
    }
}
