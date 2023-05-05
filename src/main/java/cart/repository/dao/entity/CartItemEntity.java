package cart.repository.dao.entity;

public class CartItemEntity {

    private final Long id;
    private final Long cartId;
    private final Long itemId;

    public CartItemEntity(final Long id, final Long cartId, final Long itemId) {
        this.id = id;
        this.cartId = cartId;
        this.itemId = itemId;
    }

    public CartItemEntity(final Long id, final CartItemEntity cartItemEntity) {
        this(id, cartItemEntity.getCartId(), cartItemEntity.getItemId());
    }

    public CartItemEntity(final Long cartId, final Long itemId) {
        this(null, cartId, itemId);
    }

    public Long getId() {
        return id;
    }

    public Long getCartId() {
        return cartId;
    }

    public Long getItemId() {
        return itemId;
    }
}
