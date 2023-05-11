package cart.service.cart.dto;

public class CartAdditionProductDto {
    private final Long userId;
    private final Long productId;

    public CartAdditionProductDto(Long userId, Long productId) {
        this.userId = userId;
        this.productId = productId;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getProductId() {
        return productId;
    }
}
