package cart.dto;

import cart.dao.entity.Cart;

public class CartResponse {

    private final Long id;
    private final Long userId;
    private final Long productId;
    private final int count;

    public CartResponse(final Long id, final Long userId, final Long productId, final int count) {
        this.id = id;
        this.userId = userId;
        this.productId = productId;
        this.count = count;
    }

    public CartResponse(final Cart cart) {
        this(cart.getId(), cart.getUserId(), cart.getProductId(), cart.getCount());
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getProductId() {
        return productId;
    }

    public int getCount() {
        return count;
    }
}
