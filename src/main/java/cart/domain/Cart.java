package cart.domain;

import cart.domain.item.Item;
import cart.domain.user.User;

public class Cart {

    private final Long id;
    private final User user;
    private final Item item;

    public Cart(Long id, User user, Item item) {
        this.id = id;
        this.user = user;
        this.item = item;
    }

    public Cart(User user, Item item) {
        this(null, user, item);
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Item getItem() {
        return item;
    }
}
