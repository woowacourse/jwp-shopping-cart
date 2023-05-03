package cart.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

import javax.validation.constraints.NotNull;

public class CartRequestDto {

    @NotNull(message = "productId는 null이면 안됩니다.")
    private final Integer productId;

    @JsonCreator
    public CartRequestDto(final Integer productId) {
        this.productId = productId;
    }

    public Integer getProductId() {
        return productId;
    }
}
