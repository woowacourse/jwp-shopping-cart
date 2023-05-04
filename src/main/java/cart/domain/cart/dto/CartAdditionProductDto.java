package cart.domain.cart.dto;

public class CartAdditionProductDto {
    private final Long userId;
    private final Long cartId;

    public CartAdditionProductDto(Long userId, Long cartId) {
        this.userId = userId;
        this.cartId = cartId;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getCartId() {
        return cartId;
    }
}
