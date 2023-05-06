package cart.controller.dto;

import cart.service.dto.CartDto;
import javax.validation.constraints.NotNull;

public class CartRequest {

    @NotNull(message = "상품 id는 비어있을 수 없습니다.")
    private long productId;

    public CartRequest() {
    }

    public CartRequest(long productId) {
        this.productId = productId;
    }

    public CartDto toCartDto() {
        return new CartDto(productId);
    }

    public long getProductId() {
        return productId;
    }
}
