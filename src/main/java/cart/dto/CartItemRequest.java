package cart.dto;

import javax.validation.constraints.NotBlank;

public class CartItemRequest {

    @NotBlank(message = "상품의 이름은 비어있을 수 없습니다.")
    private Long productId;

    public CartItemRequest() {

    }

    public CartItemRequest(Long productId) {
        this.productId = productId;
    }

    public Long getProductId() {
        return productId;
    }

}
