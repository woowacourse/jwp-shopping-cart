package cart.domain;

public class Item {

    private final Long id;
    private final Long cartId;
    private final Long productId;

    public Item(Long id, Long cartId, Long productId) {
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
