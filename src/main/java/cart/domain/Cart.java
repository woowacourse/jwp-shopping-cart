package cart.domain;

public class Cart {

    /**
     * TODO: 이렇게 할 순 없을까?
     private final User user;
     private final Item item;
     */

    private final Long user_id;
    private final Long item_id;

    public Cart(final Long user_id, final Long item_id) {
        this.user_id = user_id;
        this.item_id = item_id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public Long getItem_id() {
        return item_id;
    }
}
