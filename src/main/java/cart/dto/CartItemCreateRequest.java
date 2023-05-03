package cart.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import javax.validation.constraints.NotNull;

public class CartItemCreateRequest {
    @NotNull(message = "상품 아이디를 입력해주세요.")
    private Long productId;

    @JsonCreator
    public CartItemCreateRequest(Long productId) {
        this.productId = productId;
    }

    public Long getProductId() {
        return productId;
    }
}
