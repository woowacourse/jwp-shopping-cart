package cart.dto;

import javax.validation.constraints.Positive;

public class CartRequestDto {

    @Positive
    private Long productId;

    public CartRequestDto() {
    }

    public CartRequestDto(Long productId) {
        this.productId = productId;
    }

    public Long getProductId() {
        return productId;
    }

}
