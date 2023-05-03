package cart.domain.cart;

public class ItemEntity {

    private final CartId id;
    private final Item item;

    public ItemEntity(final long id, final long userId, final long productId) {
        this.id = new CartId(id);
        this.item = new Item(userId, productId);
    }

    public ItemEntity(final long id, final Item item) {
        this.id = new CartId(id);
        this.item = item;
    }

    public long getId() {
        return id.getValue();
    }

    public long getUserId() {
        return item.getUserId();
    }

    public long getProductId() {
        return item.getProductId();
    }
}
