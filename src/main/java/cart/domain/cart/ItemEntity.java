package cart.domain.cart;

import java.util.Objects;

public class ItemEntity {

    private final CartId id;
    private final Item item;

    public ItemEntity(final long id, final long memberId, final long productId) {
        this.id = new CartId(id);
        this.item = new Item(memberId, productId);
    }

    public long getId() {
        return id.getValue();
    }

    public long getMemberId() {
        return item.getMemberId();
    }

    public long getProductId() {
        return item.getProductId();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ItemEntity that = (ItemEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
