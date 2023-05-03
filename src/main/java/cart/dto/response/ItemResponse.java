package cart.dto.response;

public class ItemResponse {

    private final Long id;
    private final Long cartId;
    private final Long productId;

    public ItemResponse(Long id, Long cartId, Long productId) {
        this.id = id;
        this.cartId = cartId;
        this.productId = productId;
    }

    public Long getId() {
        return id;
    }

    public Long getCartId() {
        return cartId;
    }

    public Long getProductId() {
        return productId;
    }
}
