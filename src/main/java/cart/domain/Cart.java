package cart.domain;

import java.util.Objects;

public class Cart {

    private final Long id;
    private final User user;
    private final Item item;

    public Cart(final Long id, final User user, final Item item) {
        this.id = id;
        this.user = user;
        this.item = item;
    }

    public Cart(final User user, final Item item) {
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

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Cart cart = (Cart) o;
        return Objects.equals(user, cart.user) && Objects.equals(item, cart.item);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, item);
    }

    @Override
    public String toString() {
        return "Cart{" +
                "user=" + user +
                ", item=" + item +
                '}';
    }
}
