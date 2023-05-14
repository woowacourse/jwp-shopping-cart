package cart.entity;

public final class CartItem {
    private final Long id;
    private final User user;
    private final Product product;

    public CartItem(final Long id, final User user, final Product product) {
        this.id = id;
        this.user = user;
        this.product = product;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Product getProduct() {
        return product;
    }
}
