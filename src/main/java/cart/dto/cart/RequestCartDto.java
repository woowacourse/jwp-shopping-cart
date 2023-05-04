package cart.dto.cart;

import javax.validation.constraints.Positive;

public class RequestCartDto {

    @Positive(message = "상품 id는 음수가 될 수 없습니다.")
    private Long productId;

    public RequestCartDto() {
    }

    public RequestCartDto(final Long productId) {
        this.productId = productId;
    }

    public Long getProductId() {
        return productId;
    }
}
