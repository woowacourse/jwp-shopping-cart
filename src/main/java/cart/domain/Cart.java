package cart.domain;

public class Cart {

    /**
     * TODO: 이렇게 할 순 없을까?
     private final User user;
     private final Item item;
     */

    private final Long userId;
    private final Long itemId;

    public Cart(final Long userId, final Long itemId) {
        this.userId = userId;
        this.itemId = itemId;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getItemId() {
        return itemId;
    }
}
