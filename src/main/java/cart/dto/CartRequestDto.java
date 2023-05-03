package cart.dto;

import javax.validation.constraints.NotNull;

public class CartRequestDto {

    @NotNull(message = "userId는 null이면 안됩니다.")
    private final int userId;
    @NotNull(message = "productId는 null이면 안됩니다.")
    private final int productId;

    public CartRequestDto(final int userId, final int productId) {
        this.userId = userId;
        this.productId = productId;
    }

    public int getUserId() {
        return userId;
    }

    public int getProductId() {
        return productId;
    }
}
